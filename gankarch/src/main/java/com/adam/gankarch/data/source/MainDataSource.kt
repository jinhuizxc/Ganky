package com.adam.gankarch.data.source

import com.adam.gankarch.common.call.ModuleResult
import com.adam.gankarch.data.entity.GankEntity
import io.reactivex.Observable

/**
 * @author yu
 * Create on 2017/10/19.
 */
interface MainDataSource {

    fun getGuideGirl(): Observable<ModuleResult<GankEntity>>

    fun getRandomGirl(): Observable<ModuleResult<GankEntity>>

    fun getListData(type: String, pageSize: String, page: String)
            : Observable<ModuleResult<List<GankEntity>>>
}