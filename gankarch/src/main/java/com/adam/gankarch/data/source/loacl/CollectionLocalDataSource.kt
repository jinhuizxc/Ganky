package com.adam.gankarch.data.source.loacl

import com.adam.gankarch.App
import com.adam.gankarch.common.call.ModuleResult
import com.adam.gankarch.data.db.GankMapper
import com.adam.gankarch.data.db.MyDb
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.data.source.CollectionDataSource
import io.reactivex.Observable

/**
 * Created by yu on 2017/10/30.
 */
class CollectionLocalDataSource : CollectionDataSource {

    private val db: MyDb by lazy { MyDb(App.instance) }

    override fun getCollection(page: Int, pageSize: Int): Observable<ModuleResult<List<GankEntity>>> {

        return Observable.create {
            try {
                val dataList = GankMapper().run {
                    db.query(page * pageSize, pageSize).map {
                        this@run.mapRevert(it)
                    }
                }
                it.onNext(ModuleResult(dataList))
                it.onComplete()
            } catch (e: Throwable) {
                it.onError(e)
            }
        }
    }

    override fun add(entity: GankEntity): Observable<ModuleResult<Void>> {
        return Observable.create {
            try {
                db.insert(GankMapper().map(entity))
                it.onNext(ModuleResult(null))
                it.onComplete()
            } catch (e: Throwable) {
                it.onError(e)
            }
        }
    }

    override fun delete(entity: GankEntity): Observable<ModuleResult<Void>> {
        return Observable.create {
            try {
                db.delete(GankMapper().map(entity))
                it.onNext(ModuleResult(null))
                it.onComplete()
            } catch (e: Throwable) {
                it.onError(e)
            }
        }
    }

    override fun isCollected(entity: GankEntity): Observable<ModuleResult<Boolean>> {
        return Observable.create {
            try {
                val bean = db.queryById(entity.id!!)
                it.onNext(ModuleResult(bean != null))
                it.onComplete()
            } catch (e: Throwable) {
                it.onError(e)
            }
        }
    }
}