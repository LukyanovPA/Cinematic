package com.pavellukyanov.cinematic.ui.popularmovie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentPopularMovieBinding
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.ui.adapters.MovieListAdapter
import com.pavellukyanov.cinematic.ui.base.BaseFragment
import com.pavellukyanov.cinematic.utils.MovieComparator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PopularMovieFragment :
    BaseFragment<PagingData<Movie>, PopularMovieViewModel>(R.layout.fragment_popular_movie) {
    private var _binding: FragmentPopularMovieBinding? = null
    private val binding get() = _binding!!
    private val vm: PopularMovieViewModel by viewModels()
    private val movieListAdapter by lazy {
        MovieListAdapter(
            MovieComparator,
            movieItemClickListener
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPopularMovieBinding.bind(view)
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