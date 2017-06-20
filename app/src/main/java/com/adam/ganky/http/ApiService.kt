package com.adam.ganky.http

import com.adam.ganky.entity.GankEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/data/{type}/{pageSize}/{page}")
    fun gank(@Path("type") type: String,
             @Path("pageSize") pageSize: String,
             @Path("page") page: String
    ): Observable<HttpResult<List<GankEntity>>>

}