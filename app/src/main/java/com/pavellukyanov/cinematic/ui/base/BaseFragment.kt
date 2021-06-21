package com.pavellukyanov.cinematic.ui.base

import androidx.fragment.app.Fragment
import com.pavellukyanov.cinematic.domain.ResourceState

abstract class BaseFragment<T : Any>(
    resourceLayout: Int
) : Fragment(resourceLayout) {

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

    }

    open fun handleErrorStateMovies(error: Throwable?) {
        handleLoadingStateMovies(false)
    }
}