package com.adam.gankarch.data.entity

import com.adam.gankarch.util.TabType
import com.alibaba.fastjson.annotation.JSONField
import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

/**
 * Created by yu on 2017/10/13.
 */
data class GankEntity(
        @JSONField(name = "_id")
        var id: String?,
        var createdAt: String?,
        var desc: String?,
        var images: List<String>?,
        var publishedAt: String?,
        var source: String?,

        var type: String?,
        var url: String?,

        var used: Boolean?,
        var who: String?
) : Serializable, MultiItemEntity {
    override fun getItemType(): Int = TabType.getTypeByName(type)
}