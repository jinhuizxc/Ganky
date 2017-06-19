package com.adam.ganky.http

import java.io.Serializable

/**
 * Gank.io的Respouse实体
 * Created by yu on 2017/6/19.
 */
class HttpResult<T> : Serializable {

    var isError: Boolean = false
    var results: T? = null
}
