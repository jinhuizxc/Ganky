package com.adam.ganky.di.moudle

import android.app.Activity
import com.adam.ganky.di.ActivityScope
import com.tbruyelle.rxpermissions2.RxPermissions


import dagger.Module
import dagger.Provides

@Module
class PermissionsModule(private val mActivity: Activity) {

    @ActivityScope
    @Provides
    internal fun provideRxpermissions(): RxPermissions = RxPermissions(mActivity)

}
