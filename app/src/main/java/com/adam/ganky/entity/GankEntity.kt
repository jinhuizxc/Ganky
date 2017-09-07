package com.adam.ganky.entity

import com.adam.ganky.util.CategoryType
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by yu on 2017/6/20.
 */
//@Entity(tableName = "gank_table")
data class GankEntity(
//        @PrimaryKey
        @SerializedName("_id")
        var id: String?,

//        @Ignore
        var createdAt: String?,

//        @ColumnInfo(name = "desc")
        var desc: String?,

//        @Ignore
        var images: List<String>?,
//        @Ignore
        var publishedAt: String?,
//        @Ignore
        var source: String?,

//        @ColumnInfo(name = "type")
        var type: String?,
//        @ColumnInfo(name = "url")
        var url: String?,

//        @Ignore
        var used: Boolean?,
//        @Ignore
        var who: String?
) : MultiItemEntity, Serializable {

//     room数据库配合kotlin的一个坑，后期不知道会不会修复，需要有一个空参数的构造，不然报错
//    constructor() : this("", "", "", null, "", "", "", "", true, "")

    // 本地数据库收藏使用，只存几个主要字段，自己手写数据库中使用
    constructor(id: String?, desc: String?, url: String?, type: String?)
            : this(id, "", desc, null, "", "", type, url, true, "")

    override fun getItemType(): Int = CategoryType.getTypeByName(type)

}