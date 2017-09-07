package com.adam.ganky.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by yu on 2017/6/20.
 */
abstract class BasePresenter<V : IView>(protected var mView: V?) : IPresenter {

    private val composite: CompositeDisposable = CompositeDisposable()

    override fun destroy() {
        mView = null
        if (!composite.isDisposed) composite.dispose()
    }

    fun addDisposable(d: Disposable) {
        composite.add(d)
    }
}