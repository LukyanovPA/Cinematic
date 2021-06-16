package com.pavellukyanov.cinematic.ui.moviedetails

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentMovieDetailsBinding
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.models.MovieDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val movieId by lazy { args.movieId }
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieDetailsBinding.bind(view)
        subscribeViewModel(movieId)
    }

    private fun subscribeViewModel(movieId: Int) {
        viewModel.getMovieDetails(movieId).observe(viewLifecycleOwner, (this::onStateReceive))
    }

    private fun onStateReceive(state: ResourceState<MovieDetails>) {
        when (state) {
            is ResourceState.Success -> handleSuccessState(state.data)
            is ResourceState.Loading -> handleLoadingState(true)
            is ResourceState.Error -> handleErrorState(state.error)
        }
    }

    private fun handleErrorState(error: Throwable) {
        handleLoadingState(false)
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.error_toast, error?.localizedMessage),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun handleLoadingState(state: Boolean) {

    }

    private fun handleSuccessState(data: MovieDetails) {
        handleLoadingState(false)
        activity?.let { activity ->
            binding.bind(
                data,
                requireContext(),
                activity
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.onDestroy()
    }
}