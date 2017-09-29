package com.adam.ganky.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.adam.ganky.R
import com.gyf.barlibrary.ImmersionBar


abstract class BaseActivity : AppCompatActivity() {

    var immersionBar: ImmersionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        injectComponent()
        setupStatusBar()
        initView()
    }

    open fun setupStatusBar() {
        immersionBar = ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimary)
                .apply { init() }
    }

    abstract fun getLayoutId(): Int

    open fun injectComponent() {}

    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        immersionBar?.destroy()
    }
}