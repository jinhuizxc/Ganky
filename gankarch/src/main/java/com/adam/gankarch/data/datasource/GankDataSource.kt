package com.adam.gankarch.data.datasource

import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.common.call.ModuleResult
import io.reactivex.Observable

/**
 * @author yu
 * Create on 2017/10/19.
 */
interface GankDataSource {

    fun getGuideGirl(): Observable<ModuleResult<GankEntity>>

    fun getRandomGirl(): Observable<ModuleResult<GankEntity>>?

    fun getListData(type: String, pageSize: String, page: String)
            : Observable<ModuleResult<List<GankEntity>>>?
}