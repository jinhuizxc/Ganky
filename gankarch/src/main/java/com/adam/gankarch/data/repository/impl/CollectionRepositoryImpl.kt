package com.adam.gankarch.data.repository.impl

import com.adam.gankarch.common.call.ModuleCall
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.data.repository.CollectionRepository
import com.adam.gankarch.data.source.CollectionDataSource
import com.adam.gankarch.data.source.loacl.CollectionLocalDataSource

/**
 * Created by yu on 2017/10/30.
 */
class CollectionRepositoryImpl : CollectionRepository {

    private val dataSource: CollectionDataSource by lazy {
        CollectionLocalDataSource()
    }

    override fun isCollected(entity: GankEntity): ModuleCall<Boolean> {
        return ModuleCall(dataSource.isCollected(entity))
    }

    override fun deleteCollection(entity: GankEntity): ModuleCall<Void> {
        return ModuleCall(dataSource.delete(entity))
    }

    override fun addCollection(entity: GankEntity): ModuleCall<Void> {
        return ModuleCall(dataSource.add(entity))
    }

    override fun getCollection(page: Int, pageSize: Int): ModuleCall<List<GankEntity>> {
        return ModuleCall(dataSource.getCollection(page, pageSize))
    }
}