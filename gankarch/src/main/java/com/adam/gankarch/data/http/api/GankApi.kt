package com.adam.gankarch.data.http.api

import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.data.http.BaseResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by yu on 2017/10/13.
 */
interface GankApi {

    @GET("api/data/{type}/{pageSize}/{page}")
    fun gank(@Path("type") type: String,
             @Path("pageSize") pageSize: String,
             @Path("page") page: String
    ): Observable<BaseResponse<List<GankEntity>>>

    // 随机获取一个妹子
    @GET("api/random/data/福利/1")
    fun getRandomGirl(): Observable<BaseResponse<List<GankEntity>>>

}