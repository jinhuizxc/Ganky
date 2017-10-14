package com.adam.gankarch.data.remote.calladapter


import android.arch.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 自定义CallAdapter将接口返回类型适配为LiveData
 * 但是不推荐这么做，因为没有处理错误情况
 */
class LiveDataCallAdapter<R>(private val responseType: Type) : CallAdapter<R, Any> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): Any {
        return object : LiveData<R>() {
            internal var started = AtomicBoolean(false)

            override fun onActive() {
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(response.body())
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {}
                    })
                }
            }
        }
    }
}
