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
abstract class ArchBaseActivity<T : ViewDataBinding> : BaseActivity() {

    lateinit var binding: T

    override fun initContentView() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        registerLifecycle(lifecycle)
    }

    open fun registerLifecycle(lifecycle: Lifecycle) {
        // 这里新增生命周期的观察者
        // lifecycle.addObserver(lifecycleObserver)
    }

    fun <V : ViewModel> getViewModel(clazz: Class<V>): V =
            ViewModelProviders.of(this, ViewModelFactory.instance).get(clazz)
}