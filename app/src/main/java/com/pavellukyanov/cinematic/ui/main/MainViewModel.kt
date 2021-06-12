package com.pavellukyanov.cinematic.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.genre.Genre
import com.pavellukyanov.cinematic.domain.genre.GenresRepo
import com.pavellukyanov.cinematic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val genresRepo: GenresRepo
) : BaseViewModel() {
    private var _genres: MutableLiveData<ResourceState<List<Genre>>> = MutableLiveData()
    private val genres: LiveData<ResourceState<List<Genre>>> get() = _genres

    fun getAllGenres(): LiveData<ResourceState<List<Genre>>> {
        _genres.postValue(ResourceState.Loading)
        dispose.add(genresRepo.getGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _genres.postValue(ResourceState.Loading) }
            .subscribe(
                { success ->
                    _genres.postValue(ResourceState.Success(success))
                },
                { error ->
                    _genres.postValue(ResourceState.Error(error))
                }
            )
        )
        return genres
    }
}