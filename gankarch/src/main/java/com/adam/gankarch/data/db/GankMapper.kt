package com.adam.gankarch.data.db

import com.adam.gankarch.data.entity.GankEntity

/**
 * Created by yu on 2017/10/30.
 */
class GankMapper : Mapper<GankEntity, GankBean> {
    override fun map(f: GankEntity): GankBean {
        return GankBean(f.id, f.desc, f.type, f.url)
    }

    override fun mapRevert(t: GankBean): GankEntity {
        return GankEntity(t.id, "", t.desc, null, "", "", t.type, t.url, false, "")
    }
}