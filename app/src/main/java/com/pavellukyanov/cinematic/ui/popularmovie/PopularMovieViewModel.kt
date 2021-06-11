package com.pavellukyanov.cinematic.ui.popularmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.pavellukyanov.cinematic.data.repository.popularmovie.PopularMovieDataSource
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.domain.genre.Genre
import com.pavellukyanov.cinematic.domain.genre.GenresRepo
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovie
import com.pavellukyanov.cinematic.domain.popularmovie.PopularMovieRepo
import com.pavellukyanov.cinematic.ui.base.BaseViewModel
import com.pavellukyanov.cinematic.utils.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val movieRepo: PopularMovieRepo,
    private val genresRepo: GenresRepo
) : BaseViewModel() {
    private var _popularMovies: MutableLiveData<ResourceState<PagingData<PopularMovie>>> =
        MutableLiveData()
    private val popularMovies: LiveData<ResourceState<PagingData<PopularMovie>>> get() = _popularMovies
    private var _genres: MutableLiveData<ResourceState<List<Genre>>> = MutableLiveData()
    private val genres: LiveData<ResourceState<List<Genre>>> get() = _genres

    fun getMovies(): LiveData<ResourceState<PagingData<PopularMovie>>> {
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

    private fun getPopularMovies(): Flowable<PagingData<PopularMovie>> {
        return Pager(
            config = PagingConfig(pageSize = Page.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { PopularMovieDataSource(movieRepo) }
        )
            .flowable
            .cachedIn(viewModelScope)
    }

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

    override fun onDestroy() {
        super.onDestroy()
    }
}