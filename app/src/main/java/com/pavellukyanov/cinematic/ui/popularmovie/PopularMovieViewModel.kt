package com.pavellukyanov.cinematic.ui.popularmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovieDataSource
import com.pavellukyanov.cinematic.ui.base.BaseViewModel
import com.pavellukyanov.cinematic.utils.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val popularMovieDataSource: PopularMovieDataSource
) : BaseViewModel() {
    private var _popularMovies = MutableLiveData<ResourceState<PagingData<Movie>>>()
    private val popularMovies: LiveData<ResourceState<PagingData<Movie>>> get() = _popularMovies

    @ExperimentalCoroutinesApi
    fun getMovies(): LiveData<ResourceState<PagingData<Movie>>> {
        _popularMovies.postValue(ResourceState.Loading)
        dispose.add(getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _popularMovies.postValue(ResourceState.Loading) }
            .subscribe(
                { success ->
                    _popularMovies.postValue(ResourceState.Success(success))
                },
                { error ->
                    _popularMovies.postValue(ResourceState.Error(error))
                }
            )
        )
        return popularMovies
    }

    @ExperimentalCoroutinesApi
    private fun getPopularMovies(): Flowable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = Page.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { popularMovieDataSource }
        )
            .flowable
            .cachedIn(viewModelScope)
    }
}