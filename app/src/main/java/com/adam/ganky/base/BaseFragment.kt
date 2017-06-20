package com.adam.ganky.base

import android.os.Bundle

/**
 * Created by yu on 2017/3/10.
 */
abstract class BaseFragment : LazyFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectComponent()
    }

    open fun injectComponent() {}


}