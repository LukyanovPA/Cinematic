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
class MovieDetailsFragment :
    BaseFragment<MovieDetails, MovieDetailsViewModel>(R.layout.fragment_movie_details) {
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val movieId by lazy { args.movieId }
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val vm: MovieDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieDetailsBinding.bind(view)
        onSubscribeViewModel(vm)
        vm.getMovieDetails(movieId)
    }

    override fun handleSuccessState(data: MovieDetails) {
        super.handleSuccessState(data)
        binding.bind(data, requireContext(), activity, peopleItemClickListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        vm.onDestroy()
    }
}