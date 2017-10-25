package com.adam.gankarch.data.http.fastjson

import com.alibaba.fastjson.JSON
import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.reflect.Type

/**
 * Created by yu on 2017/10/25.
 */
class FastJsonResponseBodyConvert<T>(private val type: Type) : Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody): T {
        return JSON.parseObject(value.string(), type)
    }
}