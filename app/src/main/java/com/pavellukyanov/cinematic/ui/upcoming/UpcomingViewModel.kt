package com.pavellukyanov.cinematic.ui.upcoming

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
import com.pavellukyanov.cinematic.domain.upcoming.UpcomingDataSource
import com.pavellukyanov.cinematic.ui.base.BaseViewModel
import com.pavellukyanov.cinematic.utils.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class UpcomingViewModel @Inject constructor(
    private val upcomingDataSource: UpcomingDataSource
) : BaseViewModel() {
    private var _upcoming = MutableLiveData<ResourceState<PagingData<Movie>>>()
    private val upcoming: LiveData<ResourceState<PagingData<Movie>>> get() = _upcoming

    @ExperimentalCoroutinesApi
    fun getMovies(): LiveData<ResourceState<PagingData<Movie>>> {
        _upcoming.postValue(ResourceState.Loading)
        dispose.add(getUpcomingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _upcoming.postValue(ResourceState.Loading) }
            .subscribe(
                { success ->
                    _upcoming.postValue(ResourceState.Success(success))
                },
                { error ->
                    _upcoming.postValue(ResourceState.Error(error))
                }
            )
        )
        return upcoming
    }

    @ExperimentalCoroutinesApi
    private fun getUpcomingMovies(): Flowable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = Page.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { upcomingDataSource }
        )
            .flowable
            .cachedIn(viewModelScope)
    }
}