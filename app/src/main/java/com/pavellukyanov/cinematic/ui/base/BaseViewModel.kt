package com.pavellukyanov.cinematic.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.pavellukyanov.cinematic.domain.ResourceState
import com.pavellukyanov.cinematic.utils.Page
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.ExperimentalCoroutinesApi

open class BaseViewModel<T : Any> : ViewModel() {
    private val dispose: CompositeDisposable = CompositeDisposable()
    private val _value = MutableLiveData<ResourceState<T>>()
    private val value get() = _value

    open fun onSetResource(value: ResourceState<T>) {
        _value.postValue(value)
    }

    open fun onSubscribeViewModel(): LiveData<ResourceState<T>> = value

    open fun Disposable.untilDestroy() {
        dispose.add(this)
    }

    open fun onDestroy() {
        dispose.clear()
    }

    @ExperimentalCoroutinesApi
    fun <K : Any, V : Any> getPagingSource(
        repo: PagingSource<K, V>
    ): Flowable<PagingData<V>> {
        return Pager(
            config = PagingConfig(pageSize = Page.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { repo }
        )
            .flowable
            .cachedIn(this.viewModelScope)
    }
}