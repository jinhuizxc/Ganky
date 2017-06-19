package com.adam.ganky.http

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/data/Android/{pageNum}/{page}")
    fun gank(@Path("pageNum") pageNum: String, @Path("page") page: String): Observable<HttpResult<List<Any>>>

}