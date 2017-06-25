package com.adam.ganky.rx

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.adam.ganky.base.IView
import com.adam.ganky.http.ErrorHandler
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * 使用无参构造不显示loading progress
 * 有参构造自动显示loading progress
 * Created by yu on 2017/6/22.
 */
abstract class ApiSubscriber<T>() : Observer<T> {

    var loadingView: IView? = null
    var progressHandler: Handler? = null

    constructor(view: IView?) : this() {
        this.loadingView = view
        if (null != loadingView) {
            progressHandler = object : Handler(Looper.getMainLooper()) {
                override fun dispatchMessage(msg: Message?) {
                    super.dispatchMessage(msg)
                    when (msg?.what) {
                        MSG_SHOW_PROGRESS -> loadingView?.showLoading()
                        MSG_HIDE_PROGRESS -> loadingView?.hideLoading()
                    }
                }
            }
        }
    }

    override fun onSubscribe(d: Disposable) {
        progressHandler?.sendEmptyMessage(MSG_SHOW_PROGRESS)
    }

    override fun onComplete() {
        progressHandler?.sendEmptyMessage(MSG_HIDE_PROGRESS)
    }

    override final fun onError(e: Throwable) {
        progressHandler?.sendEmptyMessage(MSG_HIDE_PROGRESS)
        ErrorHandler.handleException(e, loadingView != null)
        onFail(e)
    }

    open fun onFail(e: Throwable?) {}

    companion object {
        const val MSG_SHOW_PROGRESS = 1100
        const val MSG_HIDE_PROGRESS = 1101
    }
}