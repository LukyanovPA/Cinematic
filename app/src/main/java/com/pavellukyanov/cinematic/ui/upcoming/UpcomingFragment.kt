package com.pavellukyanov.cinematic.ui.upcoming

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentUpcomingBinding
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.ui.adapters.MovieListAdapter
import com.pavellukyanov.cinematic.ui.base.BaseFragment
import com.pavellukyanov.cinematic.utils.MovieComparator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UpcomingFragment :
    BaseFragment<PagingData<Movie>, UpcomingViewModel>(R.layout.fragment_upcoming) {
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private val vm: UpcomingViewModel by viewModels()
    private val movieListAdapter by lazy {
        MovieListAdapter(
            MovieComparator,
            movieItemClickListener
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpcomingBinding.bind(view)
        initRecycler()
        onSubscribeViewModel(vm)
    }

    private fun initRecycler() {
        binding.bindAdapter(requireContext(), movieListAdapter)
    }

    override fun handleSuccessState(data: PagingData<Movie>) {
        super.handleSuccessState(data)
        movieListAdapter.submitData(lifecycle, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.onDestroy()
        _binding = null
    }
}