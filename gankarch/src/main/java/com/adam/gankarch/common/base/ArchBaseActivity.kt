package com.adam.gankarch.common.base

import android.arch.lifecycle.Lifecycle
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding

/**
 * 集成Android Architecture Components的基类
 * Created by yu on 2017/10/13.
 */
abstract class ArchBaseActivity<B : ViewDataBinding> : BaseActivity() {

    lateinit var mBinding: B

    override fun initContentView() {
        mBinding = DataBindingUtil.setContentView(this, layoutId)
        registerLifecycle(lifecycle)
    }

    open fun registerLifecycle(lifecycle: Lifecycle) {
        //    这里新增生命周期的观察者
        //    lifecycle.addObserver(lifecycleObserver)
    }

}