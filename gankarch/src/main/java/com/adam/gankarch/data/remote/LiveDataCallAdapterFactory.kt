package com.adam.gankarch.data.remote

import android.arch.lifecycle.LiveData
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (CallAdapter.Factory.getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType = CallAdapter.Factory.getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = CallAdapter.Factory.getRawType(observableType)
        // 如果LiveData中的泛型类型不是BaseResponse，则不处理
        return if (!BaseResponse::class.java.isAssignableFrom(rawObservableType)) {
            null
        } else LiveDataCallAdapter<BaseResponse<*>>(observableType)
    }
}