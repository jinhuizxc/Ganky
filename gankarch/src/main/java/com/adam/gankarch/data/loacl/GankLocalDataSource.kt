package com.adam.gankarch.data.loacl

import com.adam.gankarch.common.call.ModuleResult
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.data.GankDataSource
import com.adam.gankarch.data.http.GankException
import com.adam.gankarch.data.http.SpConstants
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.EmptyUtils
import com.blankj.utilcode.util.SPUtils
import io.reactivex.Observable

/**
 * 本地DataSource
 * Created by yu on 2017/10/17.
 */
class GankLocalDataSource : GankDataSource {

    fun refreshGuideGirl(entity: GankEntity) {
        SPUtils.getInstance().put(SpConstants.GUIDE_GIRL_USED_TIME, 1)
        SPUtils.getInstance().put(SpConstants.GUIDE_GIRL_ENTITY_STR, JSON.toJSONString(entity))
    }

    override fun getGuideGirl(): Observable<ModuleResult<GankEntity>> {

        val str = SPUtils.getInstance().getString(SpConstants.GUIDE_GIRL_ENTITY_STR)
        val count = SPUtils.getInstance().getInt(SpConstants.GUIDE_GIRL_USED_TIME, 1)
        SPUtils.getInstance().put(SpConstants.GUIDE_GIRL_USED_TIME, count + 1)

        return Observable.create<ModuleResult<GankEntity>> {
            val result = if (EmptyUtils.isNotEmpty(str)) {
                ModuleResult(JSON.parseObject(str, GankEntity::class.java))
            } else {
                ModuleResult(null, GankException(errorMessage = "GuideGirl is not found from cache"))
            }
            it.onNext(result)
            it.onComplete()
        }
    }

    override fun getRandomGirl(): Observable<ModuleResult<GankEntity>> {
        // no use
        throw RuntimeException("not support")
    }

    override fun getListData(type: String, pageSize: String, page: String)
            : Observable<ModuleResult<List<GankEntity>>> {
        // no use
        throw RuntimeException("not support")
    }
}