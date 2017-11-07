package com.adam.gankarch.common.base

import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author yu
 * Create on 2017/10/13.
 */
open class BaseViewModel : ViewModel() {

    protected val mDisposables: CompositeDisposable = CompositeDisposable()

    protected fun addDisposables(disposable: Disposable) {
        mDisposables.add(disposable)
    }

    override fun onCleared() {
        mDisposables.clear()
        super.onCleared()
        Log.i("BaseViewModel", "${this.javaClass.simpleName} onCleared...")
    }

}
