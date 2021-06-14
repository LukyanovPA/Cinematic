package com.pavellukyanov.cinematic.ui.toprated

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentTopRatedBinding
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.ui.adapters.MovieListAdapter
import com.pavellukyanov.cinematic.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedFragment : Fragment(R.layout.fragment_top_rated) {
    private var _binding: FragmentTopRatedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TopRatedViewModel by viewModels()
    private val popAdapter by lazy { MovieListAdapter(TopRatedComparator) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTopRatedBinding.bind(view)
        initRecycler()
        subscribeViewModel()
    }

    private fun initRecycler() {
        with(binding) {
            recyTopRated.apply {
                adapter = popAdapter
                layoutManager = GridLayoutManager(context, Constants.POPULAR_GRID_COLUMN)
            }
        }
    }

    private fun subscribeViewModel() {
        viewModel.getMovies().observe(viewLifecycleOwner, (this::onStateReceive))
    }

    private fun onStateReceive(resourceState: ResourceState<PagingData<Movie>>) {
        when (resourceState) {
            is ResourceState.Success -> handleSuccessStateMovies(resourceState.data)
            is ResourceState.Loading -> handleLoadingStateMovies(true)
            is ResourceState.Error -> handleErrorStateMovies(resourceState.error)
        }
    }

    private fun handleSuccessStateMovies(data: PagingData<Movie>) {
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

    object TopRatedComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return when {
                oldItem.id == newItem.id -> true
                oldItem.voteAverage == newItem.voteAverage -> true
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
        _binding = null
    }
}