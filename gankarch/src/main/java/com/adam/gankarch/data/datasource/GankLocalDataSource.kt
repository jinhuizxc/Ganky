package com.adam.gankarch.data.datasource

import com.adam.gankarch.data.support.SpConstants
import com.adam.gankarch.data.bean.GankEntity
import com.adam.gankarch.data.support.GankException
import com.adam.gankarch.common.call.ModuleResult
import com.blankj.utilcode.util.EmptyUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import io.reactivex.Observable

/**
 * 本地DataSource
 * Created by yu on 2017/10/17.
 */
class GankLocalDataSource : GankDataSource {

    private val gson by lazy { Gson() }

    fun refreshGuideGirl(entity: GankEntity) {
        SPUtils.getInstance().put(SpConstants.GUIDE_GIRL_USED_TIME, 1)
        SPUtils.getInstance().put(SpConstants.GUIDE_GIRL_ENTITY_STR, Gson().toJson(entity))
    }

    override fun getGuideGirl(): Observable<ModuleResult<GankEntity>> {

        val str = SPUtils.getInstance().getString(SpConstants.GUIDE_GIRL_ENTITY_STR)
        val count = SPUtils.getInstance().getInt(SpConstants.GUIDE_GIRL_USED_TIME, 1)
        SPUtils.getInstance().put(SpConstants.GUIDE_GIRL_USED_TIME, count + 1)

        return Observable.create<ModuleResult<GankEntity>> {
            val result = if (EmptyUtils.isNotEmpty(str)) {
                ModuleResult(gson.fromJson(str, GankEntity::class.java))
            } else {
                ModuleResult(null, GankException(errorMessage = "GuideGirl is not found from cache"))
            }
            it.onNext(result)
            it.onComplete()
        }
    }

    override fun getRandomGirl(): Observable<ModuleResult<GankEntity>>? {
        // no use
        return null
    }

    override fun getListData(type: String, pageSize: String, page: String)
            : Observable<ModuleResult<List<GankEntity>>>? {
        // no use
        return null
    }
}