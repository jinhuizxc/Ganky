package com.adam.ganky.http

import com.adam.ganky.util.Constant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author yu
 * *         Create on 16/5/19.
 */
class RetrofitManager private constructor() {

    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(genericClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun genericClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                //                    .addInterceptor(new HeaderInterceptor())
                .build()
    }

    private object SingletonHolder {
        val INSTANCE = RetrofitManager()
    }

    fun <T> createService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }

    companion object {

        private val DEFAULT_TIMEOUT = 15_000L

        val instance: RetrofitManager
            get() = SingletonHolder.INSTANCE
    }

}
