package com.adam.ganky.base

import com.adam.ganky.util.ToastUtils
import javax.inject.Inject

/**
 * Created by yu on 2017/6/20.
 */
abstract class BaseMvpActivity<P : IPresenter> : BaseActivity(), IView {

    @Inject lateinit var mPresenter: P

    override fun showLoading(message: String?) {

    }

    override fun hideLoading() {

    }

    override fun showTips(message: String?) {
        ToastUtils.show(message)
    }

    /**
     * 初始化dagger注入，完成后需要调用mPresenter.attachView注入view
     */
    override abstract fun injectComponent()

    override fun onDestroy() {
        mPresenter.destroy()
        super.onDestroy()
    }
}