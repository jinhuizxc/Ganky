package com.adam.gankarch.common.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * BaseActivity
 * Created by yu on 2017/10/13.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
    }

    open fun initContentView() {
        setContentView(getLayoutId())
    }

    abstract fun getLayoutId(): Int

}