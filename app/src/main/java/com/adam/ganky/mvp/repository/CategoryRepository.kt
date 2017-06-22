package com.adam.ganky.mvp.repository

import com.adam.ganky.entity.GankEntity
import com.adam.ganky.http.ApiService
import com.adam.ganky.http.RepositoryManager
import com.adam.ganky.mvp.ICategory
import com.adam.ganky.rx.RxUtils
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by yu on 2017/6/20.
 */
class CategoryRepository
@Inject constructor(private var rpm: RepositoryManager) : ICategory.Repository {

    override fun loadData(type: String, pageSize: Int, pageNum: Int): Observable<List<GankEntity>> {
        return rpm.obtainService(ApiService::class.java)
                .gank(type, pageSize.toString(), pageNum.toString()).compose(RxUtils.parseResult())
    }
}