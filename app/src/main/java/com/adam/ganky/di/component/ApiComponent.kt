package com.adam.ganky.di.component

import com.adam.ganky.di.moudle.ApiModule
import com.adam.ganky.di.moudle.AppModule
import com.adam.ganky.http.ApiService
import com.adam.ganky.ui.CategoryFragment
import dagger.Component
import javax.inject.Singleton

/**
 * 提供api
 * @author yu
 * *         Create on 2016/12/11.
 */
@Singleton
@Component(modules = arrayOf(ApiModule::class, AppModule::class))
interface ApiComponent {
    fun apiService(): ApiService

    fun inject(fragment: CategoryFragment)
}
