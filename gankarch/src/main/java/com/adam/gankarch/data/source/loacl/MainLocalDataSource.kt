package com.adam.gankarch.data.source.loacl

import android.util.Log
import com.adam.gankarch.common.base.Resp
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.data.http.GankException
import com.adam.gankarch.data.http.SpConstants
import com.adam.gankarch.data.source.MainDataSource
import com.blankj.utilcode.util.EmptyUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import io.reactivex.Observable

/**
 * 本地DataSource
 * Created by yu on 2017/10/17.
 */
class MainLocalDataSource : MainDataSource {

    fun refreshGuideGirl(entity: GankEntity) {
        SPUtils.getInstance().put(SpConstants.GUIDE_GIRL_USED_TIME, 0)
        SPUtils.getInstance().put(SpConstants.GUIDE_GIRL_ENTITY_STR, Gson().toJson(entity))
    }

    override fun getGuideGirl(): Observable<Resp<GankEntity>> {

        Log.i("GankLocalDataSource", "get a girl from cache~~~")

        val str = SPUtils.getInstance().getString(SpConstants.GUIDE_GIRL_ENTITY_STR)
        val count = SPUtils.getInstance().getInt(SpConstants.GUIDE_GIRL_USED_TIME, 0)
        SPUtils.getInstance().put(SpConstants.GUIDE_GIRL_USED_TIME, count + 1)

        return Observable.create<Resp<GankEntity>> {
            val result = if (EmptyUtils.isNotEmpty(str)) {
                Resp(Gson().fromJson(str, GankEntity::class.java))
            } else {
                Resp(null, GankException(errorMessage = "GuideGirl is not found from cache"))
            }
            it.onNext(result)
            it.onComplete()
        }
    }

    override fun getRandomGirl(): Observable<Resp<GankEntity>> {
        // no use
        throw RuntimeException("not support")
    }

    override fun getListData(type: String, pageSize: String, page: String)
            : Observable<Resp<List<GankEntity>>> {
        // no use
        throw RuntimeException("not support")
    }
}