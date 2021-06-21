package com.pavellukyanov.cinematic.ui.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.ui.adapters.MovieItemClickListener
import com.pavellukyanov.cinematic.ui.main.MainFragmentDirections

abstract class BaseFragment<T : Any>(
    resourceLayout: Int
) : Fragment(resourceLayout) {

    protected val movieItemClickListener = object : MovieItemClickListener {
        override fun onItemClicked(movieId: Int) {
            val action =
                MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(
                    movieId
                )
            findNavController().navigate(action)
        }
    }

    open fun onStateReceive(resourceState: ResourceState<T>) {
        when (resourceState) {
            is ResourceState.Success -> handleSuccessStateMovies(resourceState.data)
            is ResourceState.Loading -> handleLoadingStateMovies(true)
            is ResourceState.Error -> handleErrorStateMovies(resourceState.error)
        }
    }

    open fun handleSuccessStateMovies(data: T) {
        handleLoadingStateMovies(false)
    }

    open fun handleLoadingStateMovies(state: Boolean) {
        //test token
    }

    open fun handleErrorStateMovies(error: Throwable?) {
        handleLoadingStateMovies(false)
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.error_toast, error?.localizedMessage),
            Toast.LENGTH_LONG
        ).show()
    }
}