package com.adam.gankarch.common.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.View
import com.adam.gankarch.common.ViewModelFactory

/**
 * Created by yu on 2017/10/13.
 */
abstract class ArchBaseFragment<T : ViewDataBinding> : BaseFragment() {

    lateinit var binding: T

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind(view)
        registerLifecycle(lifecycle)
        super.onViewCreated(view, savedInstanceState)
    }

    open fun registerLifecycle(lifecycle: Lifecycle) {
        // 这里新增生命周期的观察者
        // lifecycle.addObserver(lifecycleObserver)
    }

    fun <V : ViewModel> getViewModel(clazz: Class<V>): V =
            ViewModelProviders.of(this, ViewModelFactory.instance).get(clazz)
}