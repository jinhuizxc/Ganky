package com.adam.ganky.base

import javax.inject.Inject

/**
 * Created by yu on 2017/6/20.
 */
abstract class BaseMvpFragment<P : BasePresenter<*>> : BaseFragment(), IView {

    @Inject lateinit var mPresenter: P

    override fun showLoading() {
        showLoading(null)
    }

    override fun showLoading(message: String?) {
    }

    override fun hideLoading() {
    }

    override abstract fun injectComponent()

    override fun onDestroyView() {
        mPresenter.destroy()
        super.onDestroyView()
    }
}