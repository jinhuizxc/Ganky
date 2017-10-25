package com.adam.gankarch.data.http.fastjson

import com.alibaba.fastjson.serializer.SerializeConfig
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by yu on 2017/10/25.
 */
class FastJsonConverterFactory
private constructor(private val serializeConfig: SerializeConfig) : Converter.Factory() {

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>,
                                      methodAnnotations: Array<Annotation>, retrofit: Retrofit)
            : Converter<*, RequestBody> {
        return FastJsonRequestBodyConverter<Any>(serializeConfig)
    }

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>,
                                       retrofit: Retrofit): Converter<ResponseBody, *> {
        return FastJsonResponseBodyConvert<Any>(type)
    }

    companion object {
        @JvmOverloads
        fun create(serializeConfig: SerializeConfig = SerializeConfig.getGlobalInstance())
                : FastJsonConverterFactory = FastJsonConverterFactory(serializeConfig)
    }
}
