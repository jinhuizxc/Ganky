package com.adam.ganky.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {

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