package com.adam.gankarch.data.remote.api

import android.arch.lifecycle.LiveData
import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.remote.BaseResponse
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
    ): LiveData<BaseResponse<List<GankEntity>>>

    // 随机获取一个妹子
    @GET("api/random/data/福利/1")
    fun getRandomGirl(): LiveData<BaseResponse<List<GankEntity>>>

}