package com.adam.gankarch.common.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * @author yu
 * Create on 2017/10/13.
 */
open class BaseViewModel : ViewModel() {

    val mDisposables: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        mDisposables.clear()
    }
}
