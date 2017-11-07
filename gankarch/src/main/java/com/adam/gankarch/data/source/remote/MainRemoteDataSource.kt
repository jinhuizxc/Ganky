package com.adam.gankarch.data.source.remote

import com.adam.gankarch.common.base.Resp
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.data.http.RetrofitHelper
import com.adam.gankarch.data.http.RxUtil
import com.adam.gankarch.data.http.api.GankApi
import com.adam.gankarch.data.source.MainDataSource
import io.reactivex.Observable

/**
 * 远程DataSource
 * Created by yu on 2017/10/17.
 */
class MainRemoteDataSource : MainDataSource {

    private val apiService: GankApi by lazy {
        RetrofitHelper.instance.createService(GankApi::class.java)
    }

    override fun getGuideGirl(): Observable<Resp<GankEntity>> = getRandomGirl()

    override fun getRandomGirl(): Observable<Resp<GankEntity>> {
        return apiService.getRandomGirl()
                .compose(RxUtil.httpToResp())
                .map {
                    if (it.isSuccess()) {
                        Resp(it.data!![0])
                    } else {
                        Resp(null, it.error)
                    }
                }
    }

    override fun getListData(type: String, pageSize: String, page: String): Observable<Resp<List<GankEntity>>> {
        return apiService.gank(type, pageSize, page)
                .compose(RxUtil.httpToResp())
    }

}