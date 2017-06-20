package com.adam.ganky.mvp

import com.adam.ganky.entity.GankEntity
import com.adam.ganky.http.ApiService
import com.adam.ganky.rx.RxUtils
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by yu on 2017/6/20.
 */
class CategoryRepository : ICategory.Repository {

    @Inject lateinit var api: ApiService

    override fun loadData(type: String, pageSize: Int, pageNum: Int): Observable<List<GankEntity>> {
        return api.gank(type, pageSize.toString(), pageNum.toString()).compose(RxUtils.parseResult())
    }
}