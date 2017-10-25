package com.adam.gankarch.common.base

import android.arch.lifecycle.ViewModel
import com.adam.gankarch.common.call.RepositoryDelegate
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author yu
 * Create on 2017/10/13.
 */
open class BaseViewModel : ViewModel() {

    private val mRepositoryDelegate = RepositoryDelegate()

    protected val mDisposables: CompositeDisposable = CompositeDisposable()

    /**
     * 获取repository用此方法，方便统一取消请求
     */
    protected fun <T : BaseRepository> getRepositoryDelegate(interfaceClz: Class<T>, impl: T): T
            = mRepositoryDelegate.getDelegate(interfaceClz, impl)

    protected fun addDisposables(disposable: Disposable) {
        mDisposables.add(disposable)
    }

    override fun onCleared() {
        mRepositoryDelegate.cancelAll()
        mDisposables.clear()
        super.onCleared()
    }

}
