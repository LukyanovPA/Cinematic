package com.pavellukyanov.cinematic.ui.main

import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.genre.Genre
import com.pavellukyanov.cinematic.domain.genre.GetGenresInteractor
import com.pavellukyanov.cinematic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val genresRepo: GetGenresInteractor
) : BaseViewModel<List<Genre>>() {

    fun getAllGenres() {
        genresRepo.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onSetResource(ResourceState.Loading) }
            .subscribe(
                { success ->
                    onSetResource(ResourceState.Success(success))
                },
                { error ->
                    onSetResource(ResourceState.Error(error))
                }
            ).untilDestroy()
    }
}