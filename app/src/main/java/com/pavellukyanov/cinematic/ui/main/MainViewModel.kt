package com.pavellukyanov.cinematic.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.genre.Genre
import com.pavellukyanov.cinematic.domain.genre.GetGenresInteractor
import com.pavellukyanov.cinematic.domain.models.Movie
import com.pavellukyanov.cinematic.domain.search.SearchMovieDataSource
import com.pavellukyanov.cinematic.ui.base.BaseViewModel
import com.pavellukyanov.cinematic.utils.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val genresRepo: GetGenresInteractor,
    private val dataSource: SearchMovieDataSource
) : BaseViewModel<List<Genre>>() {
    private val _searchResult = MutableLiveData<ResourceState<PagingData<Movie>>>()
    private val searchResult get() = _searchResult

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

    fun onSearch(query: String) {
        getPagingSource(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _searchResult.postValue(ResourceState.Loading) }
            .subscribe(
                { success ->
                    _searchResult.postValue(ResourceState.Success(success))
                },
                { error ->
                    _searchResult.postValue(ResourceState.Error(error))
                })
            .untilDestroy()
    }

    fun getSearchResult(): LiveData<ResourceState<PagingData<Movie>>> = searchResult

    private fun getPagingSource(query: String): Flowable<PagingData<Movie>> {
        dataSource.query = query
        return Pager(
            config = PagingConfig(pageSize = Page.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { dataSource }
        )
            .flowable
            .cachedIn(this.viewModelScope)
    }
}