package com.adam.gankarch.data.repository.impl

import com.adam.gankarch.common.base.Resp
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.data.http.RxUtil
import com.adam.gankarch.data.repository.CollectionRepository
import com.adam.gankarch.data.source.CollectionDataSource
import com.adam.gankarch.data.source.loacl.CollectionLocalDataSource
import io.reactivex.Observable

/**
 * Created by yu on 2017/10/30.
 */
class CollectionRepositoryImpl : CollectionRepository {

    private val dataSource: CollectionDataSource by lazy { CollectionLocalDataSource() }

    override fun isCollected(entity: GankEntity): Observable<Boolean> {
        return dataSource.isCollected(entity)
                .compose(RxUtil.ioToMain())
    }

    override fun deleteCollection(entity: GankEntity): Observable<Boolean> {
        return dataSource.delete(entity)
                .compose(RxUtil.ioToMain())
    }

    override fun addCollection(entity: GankEntity): Observable<Boolean> {
        return dataSource.add(entity)
                .compose(RxUtil.ioToMain())
    }

    override fun getCollection(page: Int, pageSize: Int): Observable<Resp<List<GankEntity>>> {
        return dataSource.getCollection(page, pageSize)
                .compose(RxUtil.ioToMain())
    }
}