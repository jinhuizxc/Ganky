package com.adam.ganky.mvp.repository

import com.adam.ganky.entity.GankEntity
import com.adam.ganky.http.ApiService
import com.adam.ganky.http.RepositoryManager
import com.adam.ganky.mvp.ICollection
import com.adam.ganky.rx.RxUtils
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by yu on 2017/6/22.
 */
class CollectionRepository
@Inject constructor() : ICollection.Repository {

    @Inject
    lateinit var rm: RepositoryManager

    override fun getCollections(page: Int, pageSize: Int): Observable<List<GankEntity>> {
        // todo this is a test
        return rm.obtainService(ApiService::class.java)
                .gank("Android", pageSize.toString(), page.toString())
                .compose(RxUtils.parseResult())
    }
}