package com.adam.ganky.di.component

import com.adam.ganky.di.moudle.AppModule
import com.adam.ganky.di.moudle.RetrofitModule
import com.adam.ganky.ui.CategoryFragment
import com.adam.ganky.ui.DetailActivity
import dagger.Component
import javax.inject.Singleton

/**
 * 提供api
 * Created by yu on 2017/6/20.
 */
@Singleton
@Component(modules = arrayOf(RetrofitModule::class, AppModule::class))
interface AppComponent {

//    fun application(): Application
//
//    fun retrofit(): Retrofit

    fun inject(fmt: CategoryFragment)
    fun inject(aty: DetailActivity)
}
