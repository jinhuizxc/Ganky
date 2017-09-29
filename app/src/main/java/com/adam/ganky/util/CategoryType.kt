package com.adam.ganky.util

/**
 * 列表的分类
 * Created by yu on 2017/6/20.
 */
enum class CategoryType(val type: Int, val nameStr: String, val position: Int) {

    ANDROID(1, "Android", 0),
    IOS(1, "iOS", 1),
    WEB(1, "前端", 2),

    GIRLS(2, "福利", 3);

    companion object {
        fun getPageTitleByPosition(position: Int): String = when (position) {
            0 -> ANDROID.nameStr
            1 -> IOS.nameStr
            2 -> WEB.nameStr
            else -> "not found"
        }

        fun getTypeByName(name: String?): Int = when (name) {
            ANDROID.nameStr -> ANDROID.type
            IOS.nameStr -> IOS.type
            else -> WEB.type
        }
    }
}