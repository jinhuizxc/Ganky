package com.adam.ganky.base

import javax.inject.Inject

/**
 * Created by yu on 2017/6/20.
 */
abstract class BaseMvpActivity<P : IPresenter> : BaseActivity(), IView {

    @Inject lateinit var mPresenter: P

    override fun showLoading() {
        showLoading(null)
    }

    override fun showLoading(message: String?) {

    }

    override fun hideLoading() {

    }

    override fun onError() {

    }

    override abstract fun injectComponent()

    override fun onDestroy() {
        mPresenter.destroy()
        super.onDestroy()
    }
}