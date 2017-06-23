package com.adam.ganky.di.moudle

import android.app.Application
import android.content.Context
import com.adam.ganky.di.ApplicationContext
import com.adam.ganky.http.RepositoryManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * 提供全局context
 * Created by yu on 2016/12/22.
 */
@Module
class AppModule(private val mApplication: Application) {

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context = mApplication

    @Singleton
    @Provides
    fun provideRepositoryManager(retrofit: Retrofit): RepositoryManager {
        return RepositoryManager(retrofit)
    }
}
