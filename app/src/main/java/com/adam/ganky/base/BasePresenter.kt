package com.adam.ganky.base

/**
 * Created by yu on 2017/6/20.
 */
abstract class BasePresenter<V : IView>(protected var mView: V?) : IPresenter {

    init {
        this.create()
    }

    override fun destroy() {
        mView = null
    }
}