package com.pavellukyanov.cinematic.ui.toprated

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentTopRatedBinding
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.ui.adapters.MovieListAdapter
import com.pavellukyanov.cinematic.ui.base.BaseFragment
import com.pavellukyanov.cinematic.utils.MovieComparator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedFragment : BaseFragment<PagingData<Movie>>(R.layout.fragment_top_rated) {
    private var _binding: FragmentTopRatedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TopRatedViewModel by viewModels()
    private val movieListAdapter by lazy { MovieListAdapter(MovieComparator, movieItemClickListener) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTopRatedBinding.bind(view)
        initRecycler()
        subscribeViewModel()
    }

    private fun initRecycler() {
        binding.bindAdapter(requireContext(), movieListAdapter)
    }

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