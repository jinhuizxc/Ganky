package com.adam.gankarch.data.api

import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.support.BaseResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by yu on 2017/10/13.
 */
public interface GankApi {

//    // 返回LiveData数据，配合自定义CallAdapter使用
//    @GET("api/data/{type}/{pageSize}/{page}")
//    fun gank(@Path("type") type: String,
//             @Path("pageSize") pageSize: String,
//             @Path("page") page: String
//    ): LiveData<BaseResponse<List<GankEntity>>>
//
//    // 随机获取一个妹子
//    @GET("api/random/data/福利/1")
//    fun getRandomGirl(): LiveData<BaseResponse<List<GankEntity>>>


    @GET("api/data/{type}/{pageSize}/{page}")
    fun gank(@Path("type") type: String,
             @Path("pageSize") pageSize: String,
             @Path("page") page: String
    ): Flowable<BaseResponse<List<GankEntity>>>

    // 随机获取一个妹子
    @GET("api/random/data/福利/1")
    fun getRandomGirl(): Flowable<BaseResponse<List<GankEntity>>>

}