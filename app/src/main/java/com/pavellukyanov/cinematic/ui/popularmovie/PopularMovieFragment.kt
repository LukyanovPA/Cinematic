package com.pavellukyanov.cinematic.ui.popularmovie

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentPopularMovieBinding
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.genre.Genre
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovie
import com.pavellukyanov.cinematic.ui.adapters.GenresListAdapter
import com.pavellukyanov.cinematic.ui.adapters.MovieListAdapter
import com.pavellukyanov.cinematic.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMovieFragment : Fragment(R.layout.fragment_popular_movie) {
    private var _binding: FragmentPopularMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PopularMovieViewModel by viewModels()
    private val popAdapter by lazy { MovieListAdapter(PopularMovieComparator) }
    private val genresAdapter by lazy { GenresListAdapter(listOf()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPopularMovieBinding.bind(view)
        initRecycler()
        subscribeViewModel()
    }

    private fun initRecycler() {
        with(binding) {
            recyPopularMovie.apply {
                adapter = popAdapter
                layoutManager = GridLayoutManager(context, Constants.POPULAR_GRID_COLUMN)
            }
            recyGenres.apply {
                adapter = genresAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun subscribeViewModel() {
        viewModel.getMovies().observe(viewLifecycleOwner, (this::onStateReceivePopularMovie))
        viewModel.getAllGenres().observe(viewLifecycleOwner, (this::onStateReceiveGenres))
    }

    private fun onStateReceivePopularMovie(resourceState: ResourceState<PagingData<PopularMovie>>) {
        when (resourceState) {
            is ResourceState.Success -> handleSuccessStateMovies(resourceState.data)
            is ResourceState.Loading -> handleLoadingStateMovies(true)
            is ResourceState.Error -> handleErrorStateMovies(resourceState.error)
        }
    }

    private fun onStateReceiveGenres(resourceState: ResourceState<List<Genre>>) {
        when (resourceState) {
            is ResourceState.Success -> handleSuccessStateGenres(resourceState.data)
            is ResourceState.Loading -> handleLoadingStateGenres(true)
            is ResourceState.Error -> handleErrorStateGenres(resourceState.error)
        }
    }

    private fun handleSuccessStateMovies(data: PagingData<PopularMovie>) {
        handleLoadingStateMovies(false)
        popAdapter.submitData(lifecycle, data)
    }

    private fun handleLoadingStateMovies(state: Boolean) {

    }

    private fun handleErrorStateMovies(error: Throwable?) {
        handleLoadingStateMovies(false)
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.error_toast, error?.localizedMessage),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun handleSuccessStateGenres(data: List<Genre>) {
        handleLoadingStateMovies(false)
        genresAdapter.apply {
            addGenres(data)
            notifyDataSetChanged()
        }
    }

    private fun handleLoadingStateGenres(state: Boolean) {

    }

    private fun handleErrorStateGenres(error: Throwable?) {
        handleLoadingStateGenres(false)
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