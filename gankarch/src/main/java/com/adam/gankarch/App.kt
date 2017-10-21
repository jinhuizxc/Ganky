package com.adam.gankarch

import android.app.Application
import com.blankj.utilcode.util.Utils


/**
 * Created by yu on 2017/10/13.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Utils.init(this)

    }

    companion object {
        lateinit var instance: App
    }

}