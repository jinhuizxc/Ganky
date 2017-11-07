package com.adam.gankarch.data.source

import com.adam.gankarch.common.base.Resp
import com.adam.gankarch.data.entity.GankEntity
import io.reactivex.Observable

/**
 * @author yu
 * Create on 2017/10/19.
 */
interface MainDataSource {

    fun getGuideGirl(): Observable<Resp<GankEntity>>

    fun getRandomGirl(): Observable<Resp<GankEntity>>

    fun getListData(type: String, pageSize: String, page: String)
            : Observable<Resp<List<GankEntity>>>
}