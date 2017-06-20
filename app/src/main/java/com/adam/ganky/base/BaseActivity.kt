package com.adam.ganky.base

import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity


abstract class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectComponent()
        initView()
    }

    protected abstract fun initView()

    open fun injectComponent() {}

}