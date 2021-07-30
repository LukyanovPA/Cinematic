package com.pavellukyanov.cinematic.ui.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pavellukyanov.cinematic.R
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.ui.adapters.MovieItemClickListener
import com.pavellukyanov.cinematic.ui.adapters.PeopleItemClickListener
import com.pavellukyanov.cinematic.ui.main.MainFragmentDirections
import com.pavellukyanov.cinematic.ui.moviedetails.MovieDetailsFragmentDirections
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment<T : Any, VM : BaseViewModel<T>>(
    resourceLayout: Int
) : Fragment(resourceLayout) {

    private val dispose = CompositeDisposable()
    protected val movieItemClickListener = object : MovieItemClickListener {
        override fun onItemClicked(movieId: Int) {
            val action =
                MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(
                    movieId
                )
            findNavController().navigate(action)
        }
    }

    protected val peopleItemClickListener = object : PeopleItemClickListener {
        override fun onItemClicked(id: Int) {
            val action =
                MovieDetailsFragmentDirections.actionMovieDetailsFragmentToPeopleDetailsFragment(
                    id
                )
            findNavController().navigate(action)
        }

    }

    open fun onSubscribeViewModel(vm: VM) {
        vm.onSubscribeViewModel().observe(viewLifecycleOwner, (this::onStateReceive))
    }

    open fun onStateReceive(resourceState: ResourceState<T>) {
        when (resourceState) {
            is ResourceState.Success -> handleSuccessState(resourceState.data)
            is ResourceState.Loading -> handleLoadingState(true)
            is ResourceState.Error -> handleErrorState(resourceState.error)
        }
    }

    open fun handleSuccessState(data: T) {
        handleLoadingState(false)
    }

    open fun handleLoadingState(state: Boolean) {

    }

    open fun handleErrorState(error: Throwable?) {
        handleLoadingState(false)
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.error_toast, error?.localizedMessage),
            Toast.LENGTH_LONG
        ).show()
    }

    open fun Disposable.untilDestroy() {
        dispose.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        dispose.clear()
    }
}