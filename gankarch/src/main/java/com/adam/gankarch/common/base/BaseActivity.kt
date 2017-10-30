package com.adam.gankarch.common.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.adam.gankarch.viewmodel.ViewModelFactory

/**
 * BaseActivity
 * Created by yu on 2017/10/13.
 */
abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
    }

    open fun initContentView() {
        setContentView(layoutId)
    }

    fun <VM : ViewModel> createViewModel(clazz: Class<VM>): VM =
            ViewModelProviders.of(this, ViewModelFactory.instance).get(clazz)

}