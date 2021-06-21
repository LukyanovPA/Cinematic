package com.pavellukyanov.cinematic.ui.popularmovie

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentPopularMovieBinding
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.ui.adapters.MovieItemClickListener
import com.pavellukyanov.cinematic.ui.adapters.MovieListAdapter
import com.pavellukyanov.cinematic.ui.base.BaseFragment
import com.pavellukyanov.cinematic.ui.main.MainFragmentDirections
import com.pavellukyanov.cinematic.utils.Constants
import com.pavellukyanov.cinematic.utils.MovieComparator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMovieFragment : BaseFragment<PagingData<Movie>>(R.layout.fragment_popular_movie) {
    private var _binding: FragmentPopularMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PopularMovieViewModel by viewModels()
    private val popAdapter by lazy {
        MovieListAdapter(
            MovieComparator,
            movieItemClickListener
        )
    }

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
                layoutManager = GridLayoutManager(context, Constants.MOVIE_LIST_GRID_COLUMN)
            }
        }
    }

    private fun subscribeViewModel() {
        viewModel.getMovies().observe(viewLifecycleOwner, (this::onStateReceive))
    }

    override fun handleSuccessStateMovies(data: PagingData<Movie>) {
        super.handleSuccessStateMovies(data)
        popAdapter.submitData(lifecycle, data)
    }

    override fun handleLoadingStateMovies(state: Boolean) {
        super.handleLoadingStateMovies(state)
        //todo
    }

    override fun handleErrorStateMovies(error: Throwable?) {
        super.handleErrorStateMovies(error)
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.error_toast, error?.localizedMessage),
            Toast.LENGTH_LONG
        ).show()
    }

    private val movieItemClickListener = object : MovieItemClickListener {
        override fun onItemClicked(movieId: Int) {
            val action =
                MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(
                    movieId
                )
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
        _binding = null
    }
}