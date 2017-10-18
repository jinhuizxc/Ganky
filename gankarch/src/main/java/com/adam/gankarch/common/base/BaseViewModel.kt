package com.adam.gankarch.common.base

import android.arch.lifecycle.ViewModel
import com.adam.gankarch.data.support.GankException
import com.blankj.utilcode.util.ToastUtils
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

    fun doError(ex: Throwable) {
        when (ex) {
            is GankException -> ToastUtils.showShort(ex.errorMessage)
            else -> ToastUtils.showShort("网络繁忙，请稍后再试！")
        }
    }
}
