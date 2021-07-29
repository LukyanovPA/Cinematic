package com.pavellukyanov.cinematic.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentMainBinding
import com.pavellukyanov.cinematic.domain.genre.Genre
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.ui.adapters.AdapterDivider
import com.pavellukyanov.cinematic.ui.adapters.GenresListAdapter
import com.pavellukyanov.cinematic.ui.adapters.SearchResultAdapter
import com.pavellukyanov.cinematic.ui.adapters.ViewPagerAdapter
import com.pavellukyanov.cinematic.ui.nowplaying.NowPlayingFragment
import com.pavellukyanov.cinematic.ui.popularmovie.PopularMovieFragment
import com.pavellukyanov.cinematic.ui.toprated.TopRatedFragment
import com.pavellukyanov.cinematic.ui.upcoming.UpcomingFragment
import com.pavellukyanov.cinematic.utils.ZoomOutFadePageTransformer
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.concurrent.TimeUnit

private val dispose = CompositeDisposable()

fun Disposable.untilDestroy() {
    dispose.add(this)
}

@ExperimentalCoroutinesApi
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
            (tab?.customView?.findViewById(R.id.tvTabItem) as TextView).setTextColor(
                context.getColor(R.color.release_and_director)
            )
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            (tab?.customView?.findViewById(R.id.tvTabItem) as TextView).setTextColor(
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

fun FragmentMainBinding.search(vm: MainViewModel) {
    Flowable.create(FlowableOnSubscribe<String> { subscriber ->
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                subscriber.onNext(newText!!)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                subscriber.onNext(query!!)
                return false
            }
        })
    }, BackpressureStrategy.BUFFER)
        .debounce(300, TimeUnit.MILLISECONDS)
        .filter { text -> text.isNotEmpty() }
        .distinctUntilChanged()
        .subscribe { text ->
            vm.onSearch(text)
        }
        .untilDestroy()
}

fun RecyclerView.bind(
    mAdapter: SearchResultAdapter,
    list: List<Movie>,
    context: Context
) {
    adapter = mAdapter
    addItemDecoration(AdapterDivider(context, R.drawable.item_rectangle))
    layoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    mAdapter.apply {
        addItems(list)
        notifyDataSetChanged()
    }
}

fun FragmentMainBinding.closeSearch() {
    logo.isVisible = true
    pager.isVisible = true
    searchResult.isVisible = false
}

fun FragmentMainBinding.onDestroy() {
    dispose.dispose()
}