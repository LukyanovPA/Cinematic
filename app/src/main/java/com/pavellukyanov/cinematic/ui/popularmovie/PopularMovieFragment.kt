package com.pavellukyanov.cinematic.ui.popularmovie

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentPopularMovieBinding
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovie
import com.pavellukyanov.cinematic.ui.popularmovie.adapters.PopularMovieAdapter
import com.pavellukyanov.cinematic.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMovieFragment : Fragment(R.layout.fragment_popular_movie) {
    private var _binding: FragmentPopularMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PopularMovieViewModel by viewModels()
    private val popAdapter by lazy { PopularMovieAdapter(PopularMovieComparator) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPopularMovieBinding.bind(view)
        initRecycler()
        subscribePopularMovies()
    }

    private fun initRecycler() {
        binding.recyPopularMovie.apply {
            adapter = popAdapter
            layoutManager = GridLayoutManager(context, Constants.POPULAR_GRID_COLUMN)
        }
    }

    private fun subscribePopularMovies() {
        viewModel.getMovies().observe(viewLifecycleOwner, (this::onStateReceive))
    }

    private fun onStateReceive(resourceState: ResourceState<PagingData<PopularMovie>>) {
        when (resourceState) {
            is ResourceState.Success -> handleSuccessState(resourceState.data)
            is ResourceState.Loading -> handleLoadingState(true)
            is ResourceState.Error -> handleErrorState(resourceState.error)
        }
    }

    private fun handleSuccessState(data: PagingData<PopularMovie>) {
        handleLoadingState(false)
        popAdapter.submitData(lifecycle, data)
    }

    private fun handleLoadingState(state: Boolean) {

    }

    private fun handleErrorState(error: Throwable?) {
        handleLoadingState(false)
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.error_toast, error?.localizedMessage),
            Toast.LENGTH_LONG
        ).show()
    }

    object PopularMovieComparator : DiffUtil.ItemCallback<PopularMovie>() {
        override fun areItemsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean {
            return when {
                oldItem.id == newItem.id -> true
                oldItem.voteAverage == newItem.voteAverage -> true
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
        _binding = null
    }
}