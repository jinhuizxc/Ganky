package com.adam.gankarch.data.source

import com.adam.gankarch.common.base.Resp
import com.adam.gankarch.data.entity.GankEntity
import io.reactivex.Observable

/**
 * Created by yu on 2017/10/30.
 */
interface CollectionDataSource {
    fun getCollection(page: Int, pageSize: Int): Observable<Resp<List<GankEntity>>>
    fun add(entity: GankEntity): Observable<Boolean>
    fun delete(entity: GankEntity): Observable<Boolean>
    fun isCollected(entity: GankEntity): Observable<Boolean>
}