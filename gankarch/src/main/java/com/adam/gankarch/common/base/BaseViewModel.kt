package com.adam.gankarch.common.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author yu
 * Create on 2017/10/13.
 */
open class BaseViewModel : ViewModel() {
    private val mDisposables: CompositeDisposable = CompositeDisposable()

    fun addDisposables(d: Disposable) {
        mDisposables.add(d)
    }

    override fun onCleared() {
        super.onCleared()
        mDisposables.clear()
    }
}
