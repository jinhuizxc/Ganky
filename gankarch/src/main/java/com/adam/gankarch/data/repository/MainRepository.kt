package com.adam.gankarch.data.repository

import com.adam.gankarch.common.base.BaseRepository
import com.adam.gankarch.common.base.Resp
import com.adam.gankarch.data.entity.GankEntity
import io.reactivex.Observable

/**
 * Created by yu on 2017/10/17.
 */
interface MainRepository : BaseRepository {

    fun getGuideGirl(): Observable<Resp<GankEntity>>

    fun getRandomGirl(): Observable<Resp<GankEntity>>

    fun getListData(type: String, pageSize: String, page: String): Observable<Resp<List<GankEntity>>>

}