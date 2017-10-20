package com.adam.gankarch.common.base

import android.arch.lifecycle.ViewModel
import com.adam.gankarch.common.call.RepositoryDelegate
import io.reactivex.disposables.CompositeDisposable

/**
 * @author yu
 * Create on 2017/10/13.
 */
open class BaseViewModel : ViewModel() {

    private val mRepositoryDelegate = RepositoryDelegate()

    val mDisposables: CompositeDisposable = CompositeDisposable()

    /**
     * 获取repository用此方法，方便统一取消请求
     */
    fun <T> createRepository(interfaceClz: Class<T>, impl: Any): T {
        return mRepositoryDelegate.getRepository(interfaceClz, impl)
    }

    override fun onCleared() {
        super.onCleared()
        mRepositoryDelegate.cancelAll()
        mDisposables.clear()
    }

}
