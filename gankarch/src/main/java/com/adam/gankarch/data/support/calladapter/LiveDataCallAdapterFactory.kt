package com.adam.gankarch.data.support.calladapter

import android.arch.lifecycle.LiveData
import com.adam.gankarch.data.support.BaseResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 自定义CallAdapter将接口返回类型适配为LiveData
 * 但是不推荐这么做，因为没有处理错误情况
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>,
                     retrofit: Retrofit): CallAdapter<*, *>? {
        return when {
            getRawType(returnType) == LiveData::class.java -> {
                val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
                val rawObservableType = getRawType(observableType)
                // 如果LiveData中的泛型类型不是BaseResponse，则不处理
                if (BaseResponse::class.java.isAssignableFrom(rawObservableType)) {
                    LiveDataCallAdapter<Any>(observableType)
                } else null
            }
            else -> null
        }

    }
}