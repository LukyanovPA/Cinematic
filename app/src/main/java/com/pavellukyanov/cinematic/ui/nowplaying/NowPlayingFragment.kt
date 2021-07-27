package com.pavellukyanov.cinematic.ui.nowplaying

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentNowPlayingBinding
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.ui.adapters.MovieListAdapter
import com.pavellukyanov.cinematic.ui.base.BaseFragment
import com.pavellukyanov.cinematic.utils.MovieComparator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NowPlayingFragment :
    BaseFragment<PagingData<Movie>, NowPlayingViewModel>(R.layout.fragment_now_playing) {
    private var _binding: FragmentNowPlayingBinding? = null
    private val binding get() = _binding!!
    private val vm: NowPlayingViewModel by viewModels()
    private val popAdapter by lazy { MovieListAdapter(MovieComparator, movieItemClickListener) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNowPlayingBinding.bind(view)
        initRecycler()
        onSubscribeViewModel(vm)
    }

    private fun initRecycler() {
        binding.bindAdapter(requireContext(), popAdapter)
    }

    override fun handleSuccessState(data: PagingData<Movie>) {
        super.handleSuccessState(data)
        popAdapter.submitData(lifecycle, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.onDestroy()
        _binding = null
    }
}