package com.adam.ganky.base

/**
 * Created by yu on 2017/6/20.
 */
abstract class BasePresenter<V : IView>(protected var mView: V?) : IPresenter {

    init {
        // 回调create方法时mView为空，所以不要做view操作
        this.create()
    }

    override fun destroy() {
        mView = null
    }
}