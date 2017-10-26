package com.adam.gankarch.common.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adam.gankarch.common.ViewModelFactory

/**
 * BaseFragment
 * Created by yu on 2017/6/19.
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView(view: View?)

    abstract fun initData()

    fun <V : ViewModel> getViewModel(clazz: Class<V>): V =
            ViewModelProviders.of(this, ViewModelFactory.instance).get(clazz)
}