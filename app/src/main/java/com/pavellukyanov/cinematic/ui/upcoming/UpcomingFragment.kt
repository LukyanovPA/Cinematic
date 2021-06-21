package com.pavellukyanov.cinematic.ui.upcoming

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.databinding.FragmentUpcomingBinding
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.ui.adapters.MovieItemClickListener
import com.pavellukyanov.cinematic.ui.adapters.MovieListAdapter
import com.pavellukyanov.cinematic.ui.base.BaseFragment
import com.pavellukyanov.cinematic.utils.Constants
import com.pavellukyanov.cinematic.utils.MovieComparator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : BaseFragment<PagingData<Movie>>(R.layout.fragment_upcoming) {
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UpcomingViewModel by viewModels()
    private val upcomingAdapter by lazy {
        MovieListAdapter(
            MovieComparator,
            movieItemClickListener
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpcomingBinding.bind(view)
        initRecycler()
        subscribeViewModel()
    }

    private fun initRecycler() {
        with(binding) {
            recyUpcoming.apply {
                adapter = upcomingAdapter
                layoutManager = GridLayoutManager(context, Constants.MOVIE_LIST_GRID_COLUMN)
            }
        }
    }

    private fun subscribeViewModel() {
        viewModel.getMovies().observe(viewLifecycleOwner, (this::onStateReceive))
    }

    override fun handleSuccessStateMovies(data: PagingData<Movie>) {
        super.handleSuccessStateMovies(data)
        upcomingAdapter.submitData(lifecycle, data)
    }

    override fun handleLoadingStateMovies(state: Boolean) {
        super.handleLoadingStateMovies(state)
        //обработать
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

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
        _binding = null
    }
}