package com.adam.ganky

import android.app.Application
import android.content.Context
import com.adam.ganky.di.component.ApiComponent
import com.adam.ganky.di.moudle.ApiModule
import com.adam.ganky.di.moudle.AppModule
import com.adam.ganky.util.Constant
import okhttp3.HttpUrl
import okhttp3.Interceptor

/**
 * Created by yu on 2017/6/20.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        apiComponent = DaggerApiComponent.builder()
                .appModule(AppModule(this))
                .apiModule(ApiModule.Builder()
                        .baseUrl(HttpUrl.parse("http://gank.io/")!!)
                        .interceptors(interceptors)
                        .create())
                .build()
    }

    val interceptors: List<Interceptor> = arrayListOf(
            Interceptor { chain ->
                val request = chain.request().newBuilder()
//                        .addHeader("token", "qwe")
                        .build()
                chain.proceed(request)
            }
    )

    companion object {
        var context: Context? = null
            private set
        var apiComponent: ApiComponent? = null
            private set
    }
}