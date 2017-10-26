package com.adam.gankarch.util

/**
 * Created by yu on 2017/10/23.
 */
object TabType {

    fun getPageTitleByPosition(position: Int): String =
            when (position) {
                0 -> "Android"
                1 -> "iOS"
                2 -> "福利"
                else -> throw IllegalStateException("wrong position: $position")
            }

    /**
     * 列表条目类型
     */
    fun getTypeByName(name: String?): Int =
            when (name) {
                "Android", "iOS" -> 0
                "福利" -> 1
                else -> throw IllegalStateException("wrong type: $name")
            }

}