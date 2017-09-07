package com.adam.ganky

import android.app.Application
import android.content.Context
import com.adam.ganky.di.component.AppComponent
import com.adam.ganky.di.component.DaggerAppComponent
import com.adam.ganky.di.moudle.AppModule
import com.adam.ganky.di.moudle.RetrofitModule
import com.adam.ganky.util.AppManager
import com.adam.ganky.util.Constant
import okhttp3.HttpUrl
import okhttp3.Interceptor
import kotlin.properties.Delegates

/**
 * app
 * Created by yu on 2017/6/20.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        AppManager.init(this)

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .retrofitModule(RetrofitModule.Builder()
                        .baseUrl(HttpUrl.parse(Constant.BASE_URL)!!)
                        .interceptors(interceptors)
                        .create())
                .build()
    }

    // 添加okhttp拦截器，这里只是个示例...
    private val interceptors: List<Interceptor> = listOf(
            Interceptor {
                val request = it.request().newBuilder()
                        .addHeader("token", "this is a token for test")
                        .build()
                it.proceed(request)
            }
    )

    companion object {
        var context: Context by Delegates.notNull()
            private set
        var appComponent: AppComponent? = null
            private set
    }
}