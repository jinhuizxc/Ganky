package com.adam.gankarch

import android.app.Application
import com.blankj.utilcode.util.Utils
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * Created by yu on 2017/10/13.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Utils.init(this)
        
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(DebugTree())
    }

    companion object {
        lateinit var instance: App
    }

}