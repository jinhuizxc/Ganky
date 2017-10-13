package com.adam.gankarch.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * RetrofitHelper
 * Created by yu on 2017/10/13.
 */
class RetrofitHelper private constructor() {
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .client(genericClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
    }

    private fun genericClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.w(message) }))
                .build()
    }

    fun <T> createService(clazz: Class<T>): T = retrofit.create(clazz)

    companion object {
        private val DEFAULT_TIMEOUT = 15_000L
        val instance = RetrofitHelper()
    }

}
