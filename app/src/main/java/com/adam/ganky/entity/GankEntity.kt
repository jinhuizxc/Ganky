package com.adam.ganky.entity

import com.adam.ganky.util.CategoryType
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName

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
) : MultiItemEntity {

    override fun getItemType(): Int {
        return CategoryType.getTypeByName(type)
    }
}