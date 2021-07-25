package com.pavellukyanov.cinematic.ui.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentMovieDetailsBinding
import com.pavellukyanov.cinematic.domain.models.MovieDetails
import com.pavellukyanov.cinematic.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<MovieDetails>(R.layout.fragment_movie_details) {
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

    override fun handleSuccessStateMovies(data: MovieDetails) {
        super.handleSuccessStateMovies(data)
        binding.bindMovieDetails(data, requireContext(), activity)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.onDestroy()
    }
}