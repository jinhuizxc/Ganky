package com.adam.ganky.base

import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity


abstract class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        injectComponent()
        initView()
    }

    abstract fun getLayoutId(): Int

    open fun injectComponent() {}

    abstract fun initView()

}