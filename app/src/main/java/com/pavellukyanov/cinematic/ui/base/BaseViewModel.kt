package com.pavellukyanov.cinematic.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    protected val dispose: CompositeDisposable = CompositeDisposable()

    open fun onDestroy() {
        dispose.dispose()
    }
}