package com.pavellukyanov.cinematic.ui.popularmovie

import androidx.paging.PagingData
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovieDataSource
import com.pavellukyanov.cinematic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val repo: PopularMovieDataSource
) : BaseViewModel<PagingData<Movie>>() {
    init {
        getPagingSource(repo)
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