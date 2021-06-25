package com.pavellukyanov.cinematic.ui.toprated

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.pavellukyanov.cinematic.domain.toprated.TopRatedDataSource
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.data.repository.toprated.TopRatedRepo
import com.pavellukyanov.cinematic.ui.base.BaseViewModel
import com.pavellukyanov.cinematic.utils.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class TopRatedViewModel @Inject constructor(
    private val topRatedDataSource: TopRatedDataSource
) : BaseViewModel() {
    private var _topRated = MutableLiveData<ResourceState<PagingData<Movie>>>()
    private val topRated: LiveData<ResourceState<PagingData<Movie>>> get() = _topRated

    @ExperimentalCoroutinesApi
    fun getMovies(): LiveData<ResourceState<PagingData<Movie>>> {
        _topRated.postValue(ResourceState.Loading)
        dispose.add(getTopRatedMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _topRated.postValue(ResourceState.Loading) }
            .subscribe(
                { success ->
                    _topRated.postValue(ResourceState.Success(success))
                },
                { error ->
                    _topRated.postValue(ResourceState.Error(error))
                }
            )
        )
        return topRated
    }

    @ExperimentalCoroutinesApi
    private fun getTopRatedMovies(): Flowable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = Page.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { topRatedDataSource }
        )
            .flowable
            .cachedIn(viewModelScope)
    }
}