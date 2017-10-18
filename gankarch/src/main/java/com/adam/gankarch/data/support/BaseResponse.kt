package com.adam.gankarch.data.support

import java.io.Serializable

/**
 * 接口返回数据实体结构
 * Created by yu on 2017/10/13.
 */
class BaseResponse<R> : Serializable {
    // 以下gank.io接口中判断业务是否成功的字段，根据自己的接口修改
    var error: Boolean = false
    var results: R? = null


    fun isSuccess() = !error
    fun message(): String = "接口请求失败..."
}