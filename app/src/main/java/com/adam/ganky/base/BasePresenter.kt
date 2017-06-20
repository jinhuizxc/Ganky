package com.adam.ganky.base

/**
 * Created by yu on 2017/6/20.
 */
abstract class BasePresenter<V : IView> : IPresenter {

    var mView: V? = null

    init {
        // 回调create方法时mView为空，所以不要做view操作
        this.create()
    }

    open fun attachView(view: V) {
        this.mView = view
    }

    override fun destroy() {
        mView = null
    }
}