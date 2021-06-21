package com.pavellukyanov.cinematic.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.ui.base.BaseFragment

class ViewPagerAdapter(
    context: Context,
    listFragment: List<BaseFragment<*>>,
    frag: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(frag, lifecycle) {

    private val list = listFragment

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list[position]
}