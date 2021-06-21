package com.pavellukyanov.cinematic.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.models.MovieDetails
import com.pavellukyanov.cinematic.domain.moviedetail.MovieDetailsRepo
import com.pavellukyanov.cinematic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repo: MovieDetailsRepo
) : BaseViewModel() {
    private var _details = MutableLiveData<ResourceState<MovieDetails>>()
    private val details: LiveData<ResourceState<MovieDetails>> get() = _details

    fun getMovieDetails(movieId: Int): LiveData<ResourceState<MovieDetails>> {
        _details.postValue(ResourceState.Loading)
        dispose.add(repo.getMovieDetails(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _details.postValue(ResourceState.Loading) }
            .subscribe(
                { success ->
                    _details.postValue(ResourceState.Success(success))
                },
                { error ->
                    _details.postValue(ResourceState.Error(error))
                }
            )
        )
        return details
    }
}