package com.adam.gankarch.data.datasource.remote

import com.adam.gankarch.common.call.ModuleResult
import com.adam.gankarch.data.api.GankApi
import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.datasource.GankDataSource
import com.adam.gankarch.data.support.GankException
import com.adam.gankarch.data.support.RetrofitHelper
import com.adam.gankarch.data.support.RxUtil
import io.reactivex.Observable

/**
 * 远程DataSource
 * Created by yu on 2017/10/17.
 */
class GankRemoteDataSource : GankDataSource {

    private val apiService: GankApi = RetrofitHelper.instance.createService(GankApi::class.java)
    override fun getGuideGirl(): Observable<ModuleResult<GankEntity>> {
        return getRandomGirl()
    }

    override fun getRandomGirl(): Observable<ModuleResult<GankEntity>> {
        return apiService.getRandomGirl()
                .toObservable()
                .flatMap {
                    val response = it
                    Observable.create<ModuleResult<GankEntity>> {
                        val result = if (response.isSuccess()) {
                            ModuleResult(response.results!![0])
                        } else {
                            ModuleResult(null, GankException(errorMessage = response.message()))
                        }
                        it.onNext(result)
                        it.onComplete()
                    }
                }
    }

    override fun getListData(type: String, pageSize: String, page: String): Observable<ModuleResult<List<GankEntity>>> {
        return apiService.gank(type, pageSize, page)
                .toObservable()
                .flatMap(RxUtil.parseResult())
    }

}