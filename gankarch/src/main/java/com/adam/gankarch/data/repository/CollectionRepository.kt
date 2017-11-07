package com.adam.gankarch.data.repository

import com.adam.gankarch.common.base.BaseRepository
import com.adam.gankarch.common.base.Resp
import com.adam.gankarch.data.entity.GankEntity
import io.reactivex.Observable

/**
 * @author yu
 * Create on 2017/10/28.
 */
interface CollectionRepository : BaseRepository {

    // 是否已经收藏
    fun isCollected(entity: GankEntity): Observable<Boolean>

    // 删除收藏
    fun deleteCollection(entity: GankEntity): Observable<Boolean>

    // 添加到收藏
    fun addCollection(entity: GankEntity): Observable<Boolean>

    // 获取收藏
    fun getCollection(page: Int, pageSize: Int): Observable<Resp<List<GankEntity>>>
}