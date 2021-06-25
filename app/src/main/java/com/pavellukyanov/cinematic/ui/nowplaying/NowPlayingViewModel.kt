package com.pavellukyanov.cinematic.ui.nowplaying

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
import com.pavellukyanov.cinematic.domain.nowplaying.NowPlayingDataSource
import com.pavellukyanov.cinematic.ui.base.BaseViewModel
import com.pavellukyanov.cinematic.utils.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val nowPlayingDataSource: NowPlayingDataSource
) : BaseViewModel() {
    private var _nowPlaying = MutableLiveData<ResourceState<PagingData<Movie>>>()
    private val nowPlaying: LiveData<ResourceState<PagingData<Movie>>> get() = _nowPlaying

    @ExperimentalCoroutinesApi
    fun getMovies(): LiveData<ResourceState<PagingData<Movie>>> {
        _nowPlaying.postValue(ResourceState.Loading)
        dispose.add(getNowPlayingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _nowPlaying.postValue(ResourceState.Loading) }
            .subscribe(
                { success ->
                    _nowPlaying.postValue(ResourceState.Success(success))
                },
                { error ->
                    _nowPlaying.postValue(ResourceState.Error(error))
                }
            )
        )
        return nowPlaying
    }

    @ExperimentalCoroutinesApi
    private fun getNowPlayingMovies(): Flowable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = Page.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { nowPlayingDataSource }
        )
            .flowable
            .cachedIn(viewModelScope)
    }
}