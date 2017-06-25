package com.adam.ganky.entity

import com.adam.ganky.util.CategoryType
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by yu on 2017/6/20.
 */
data class GankEntity(
        @SerializedName("_id")
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
) : MultiItemEntity, Serializable {

    // 本地数据库收藏使用，只存几个主要字段
    constructor(id: String?, desc: String?, url: String?, type: String?)
            : this(id, "", desc, null, "", "", type, url, true, "")

    override fun getItemType(): Int {
        return CategoryType.getTypeByName(type)
    }

}