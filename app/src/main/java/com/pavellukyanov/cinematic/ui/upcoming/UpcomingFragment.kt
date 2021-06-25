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

@AndroidEntryPoint
class UpcomingFragment : BaseFragment<PagingData<Movie>>(R.layout.fragment_upcoming) {
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UpcomingViewModel by viewModels()
    private val movieListAdapter by lazy { MovieListAdapter(MovieComparator, movieItemClickListener) }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpcomingBinding.bind(view)
        initRecycler()
        subscribeViewModel()
    }

    private fun initRecycler() {
        binding.bindAdapter(requireContext(), movieListAdapter)
    }

    @ExperimentalCoroutinesApi
    private fun subscribeViewModel() {
        viewModel.getMovies().observe(viewLifecycleOwner, (this::onStateReceive))
    }

    override fun handleSuccessStateMovies(data: PagingData<Movie>) {
        super.handleSuccessStateMovies(data)
        movieListAdapter.submitData(lifecycle, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
        _binding = null
    }
}