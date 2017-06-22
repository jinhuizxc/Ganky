package com.adam.ganky.http

import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject

/**
 * 提供RetrofitService的管理
 * Created by yu on 2017/6/21.
 */
class RepositoryManager
@Inject constructor(private val mRetrofit: Retrofit) {

    private val mRetrofitServiceCache = LinkedHashMap<String, Any>()

    fun <T> obtainService(serviceClz: Class<T>): T {
        synchronized(mRetrofitServiceCache) {
            var service: T? = mRetrofitServiceCache[serviceClz.name] as T
            if (service == null) {
                service = mRetrofit.create(serviceClz)
                mRetrofitServiceCache.put(serviceClz.name, service!!)
            }
            return service
        }
    }

}
