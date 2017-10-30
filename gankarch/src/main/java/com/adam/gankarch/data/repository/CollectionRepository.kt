package com.adam.gankarch.data.repository

import com.adam.gankarch.common.base.BaseRepository
import com.adam.gankarch.common.call.ModuleCall
import com.adam.gankarch.data.entity.GankEntity

/**
 * @author yu
 * Create on 2017/10/28.
 */
interface CollectionRepository : BaseRepository {

    // 是否已经收藏
    fun isCollected(entity: GankEntity): ModuleCall<Boolean>

    // 删除收藏
    fun deleteCollection(entity: GankEntity): ModuleCall<Void>

    // 添加到收藏
    fun addCollection(entity: GankEntity): ModuleCall<Void>

    // 获取收藏
    fun getCollection(page: Int, pageSize: Int): ModuleCall<List<GankEntity>>
}