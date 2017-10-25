package com.adam.gankarch.data.http.fastjson

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeConfig
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Converter


/**
 * Created by yu on 2017/10/25.
 */
class FastJsonRequestBodyConverter<T>(private val serializeConfig: SerializeConfig) : Converter<T, RequestBody> {

    private val MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8")

    override fun convert(value: T): RequestBody {
        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(value, serializeConfig))
    }
}