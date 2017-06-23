package com.adam.ganky.di.component

import android.content.Context
import com.adam.ganky.di.ApplicationContext
import com.adam.ganky.di.moudle.AppModule
import com.adam.ganky.di.moudle.RetrofitModule
import com.adam.ganky.http.RepositoryManager
import dagger.Component
import javax.inject.Singleton

/**
 * 提供api
 * Created by yu on 2017/6/20.
 */
@Singleton
@Component(modules = arrayOf(RetrofitModule::class, AppModule::class))
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    fun repositoryManager(): RepositoryManager

}
