package com.adam.ganky.mvp.repository

import com.adam.ganky.entity.GankEntity
import com.adam.ganky.http.ApiService
import com.adam.ganky.http.RepositoryManager
import com.adam.ganky.mvp.IDetail
import com.adam.ganky.rx.RxUtils
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by yu on 2017/6/22.
 */
class DetailRepository
@Inject constructor(private var rpm: RepositoryManager) : IDetail.Repository {

    override fun getGirl(): Observable<List<GankEntity>> {
        return rpm.obtainService(ApiService::class.java)
                .getRandomGirl().compose(RxUtils.parseResult())
    }
}