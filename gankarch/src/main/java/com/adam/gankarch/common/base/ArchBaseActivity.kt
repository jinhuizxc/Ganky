package com.adam.gankarch.common.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import com.adam.gankarch.common.ViewModelFactory

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

    fun <VM : ViewModel> createViewModel(clazz: Class<VM>): VM =
            ViewModelProviders.of(this, ViewModelFactory.instance).get(clazz)

}