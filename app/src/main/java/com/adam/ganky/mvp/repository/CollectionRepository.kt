package com.adam.ganky.mvp.repository

import com.adam.ganky.db.MyDb
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.mvp.ICollection
import com.adam.ganky.util.AppManager
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by yu on 2017/6/22.
 */
class CollectionRepository
@Inject constructor() : ICollection.Repository {

    private val dbHelper: MyDb by lazy {
        MyDb(AppManager.appContext())
    }

    override fun getCollections(page: Int, pageSize: Int): Observable<List<GankEntity>> {
        return Observable.just(dbHelper.query(page * pageSize, pageSize))
    }

    override fun remove(entity: GankEntity) {
        dbHelper.delete(entity)
    }
}