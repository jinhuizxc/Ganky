package com.adam.gankarch.data.source.remote

import com.adam.gankarch.common.call.ModuleResult
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

    override fun getGuideGirl(): Observable<ModuleResult<GankEntity>> = getRandomGirl()

    override fun getRandomGirl(): Observable<ModuleResult<GankEntity>> {
        return apiService.getRandomGirl()
                .flatMap(RxUtil.parseResult())
                .map {
                    if (it.isSuccess()) {
                        ModuleResult(it.data!![0])
                    } else {
                        ModuleResult(null, it.error)
                    }
                }
    }

    override fun getListData(type: String, pageSize: String, page: String): Observable<ModuleResult<List<GankEntity>>> {
        return apiService.gank(type, pageSize, page)
                .flatMap(RxUtil.parseResult())
    }

}