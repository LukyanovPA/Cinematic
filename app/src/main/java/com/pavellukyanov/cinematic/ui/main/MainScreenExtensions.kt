package com.pavellukyanov.cinematic.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentMainBinding
import com.pavellukyanov.cinematic.domain.genre.Genre
import com.pavellukyanov.cinematic.ui.adapters.GenresListAdapter
import com.pavellukyanov.cinematic.ui.adapters.ViewPagerAdapter
import com.pavellukyanov.cinematic.ui.nowplaying.NowPlayingFragment
import com.pavellukyanov.cinematic.ui.popularmovie.PopularMovieFragment
import com.pavellukyanov.cinematic.ui.toprated.TopRatedFragment
import com.pavellukyanov.cinematic.ui.upcoming.UpcomingFragment
import com.pavellukyanov.cinematic.utils.ZoomOutFadePageTransformer

fun ViewPager2.bindMainViewPager(
    tabLayout: TabLayout,
    context: Context,
    layoutInflater: LayoutInflater,
    childFragmentManager: FragmentManager,
    lifecycle: Lifecycle
) {
    val category = listOf(
        context.getString(R.string.popular_movie),
        context.getString(R.string.now_playing),
        context.getString(R.string.top_rated),
        context.getString(R.string.upcoming),
    )
    val fragmentList = listOf(
        PopularMovieFragment(),
        NowPlayingFragment(),
        TopRatedFragment(),
        UpcomingFragment()
    )

    adapter = ViewPagerAdapter(
        context,
        fragmentList,
        childFragmentManager,
        lifecycle
    )
    setPageTransformer()
    offscreenPageLimit = fragmentList.size
    tabLayout.bindTabMediator(
        context,
        this,
        layoutInflater,
        category
    )
}

private fun ViewPager2.setPageTransformer() {
    val nextCardVisibleWidth =
        resources.getDimensionPixelSize(R.dimen.nex_item_visible)
    val cardInnerMargin = resources.getDimensionPixelSize(R.dimen.item_inner_margin)
    val pageTranslation = nextCardVisibleWidth + cardInnerMargin
    setPageTransformer(ZoomOutFadePageTransformer(pageTranslation))
}

private fun TabLayout.bindTabMediator(
    context: Context,
    pager: ViewPager2,
    layoutInflater: LayoutInflater,
    category: List<String>
) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            (tab?.customView?.findViewById(R.id.tvTabItem) as TextView).textSize = 10f
            (tab.customView?.findViewById(R.id.tvTabItem) as TextView).setTextColor(
                context.getColor(R.color.release_and_director)
            )
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            (tab?.customView?.findViewById(R.id.tvTabItem) as TextView).textSize = 26f
            (tab.customView?.findViewById(R.id.tvTabItem) as TextView).setTextColor(
                context.getColor(R.color.main_color)
            )
        }

    })

    TabLayoutMediator(this, pager,
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            val inflate =
                layoutInflater.inflate(R.layout.layout_tablayout_item, this, false)
            tab.customView = inflate
            inflate.findViewById<TextView>(R.id.tvTabItem).text = category[position]
        }).attach()
}

fun FragmentMainBinding.bindAdapter(
    listGenre: List<Genre>,
    context: Context
) {
    val genresAdapter = GenresListAdapter(listOf())
    recyGenres.apply {
        adapter = genresAdapter
        layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
    genresAdapter.apply {
        addGenres(listGenre)
        notifyDataSetChanged()
    }
}