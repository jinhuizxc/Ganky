package com.adam.ganky.di.moudle

import android.content.Context
import android.util.Log
import com.adam.ganky.di.ApplicationContext
import com.adam.ganky.http.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 提供ApiService
 * Created by yu on 2016/12/22.
 */
@Module
class ApiModule {

    private var mApiUrl: HttpUrl? = null
    private var timeout: Long = 0
    private var mInterceptors: List<Interceptor>? = null

    constructor(builder: Builder) {
        this.mApiUrl = builder.baseUrl
        this.timeout = builder.httpTimeOut
        this.mInterceptors = builder.interceptors
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    internal fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(mApiUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    internal fun provideOkHttpClient(@ApplicationContext context: Context, logger: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.MILLISECONDS)

        mInterceptors?.forEach {
            builder.addInterceptor(it)
        }
        return builder.addInterceptor(logger).build()
    }

    @Singleton
    @Provides
    internal fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message -> Log.e("yu", message) }.setLevel(HttpLoggingInterceptor.Level.BODY)
    }


    class Builder {
        var baseUrl: HttpUrl = HttpUrl.parse("http://gank.io/")!!
        var httpTimeOut = 10000.toLong()
        var interceptors: List<Interceptor>? = null

        fun baseUrl(baseUrl: HttpUrl): Builder {
            this.baseUrl = baseUrl
            return this
        }

        fun httpTimeOut(timeout: Long): Builder {
            this.httpTimeOut = timeout
            return this
        }

        fun interceptors(interceptors: List<Interceptor>?): Builder {
            this.interceptors = interceptors
            return this
        }

        fun create(): ApiModule {
            return ApiModule(this)
        }
    }
}
