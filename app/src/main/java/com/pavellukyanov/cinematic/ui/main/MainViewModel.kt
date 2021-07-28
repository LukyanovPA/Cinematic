package com.pavellukyanov.cinematic.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.genre.Genre
import com.pavellukyanov.cinematic.domain.genre.GetGenresInteractor
import com.pavellukyanov.cinematic.domain.search.SearchInteractor
import com.pavellukyanov.cinematic.domain.search.SearchItem
import com.pavellukyanov.cinematic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val genresRepo: GetGenresInteractor,
    private val search: SearchInteractor
) : BaseViewModel<List<Genre>>() {
    private val _searchResult = MutableLiveData<ResourceState<List<SearchItem>>>()
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
        search.invoke(query)
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

    fun getSearchResult(): LiveData<ResourceState<List<SearchItem>>> = searchResult
}