package com.adam.gankarch.data.source

import com.adam.gankarch.common.call.ModuleResult
import com.adam.gankarch.data.entity.GankEntity
import io.reactivex.Observable

/**
 * Created by yu on 2017/10/30.
 */
interface CollectionDataSource {
    fun getCollection(page: Int, pageSize: Int): Observable<ModuleResult<List<GankEntity>>>
    fun add(entity: GankEntity): Observable<ModuleResult<Void>>
    fun delete(entity: GankEntity): Observable<ModuleResult<Void>>
    fun isCollected(entity: GankEntity): Observable<ModuleResult<Boolean>>
}