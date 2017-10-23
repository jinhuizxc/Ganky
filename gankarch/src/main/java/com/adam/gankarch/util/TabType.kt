package com.adam.gankarch.util

/**
 * Created by yu on 2017/10/23.
 */
object TabType {

    fun getPageTitleByPosition(position: Int): String =
            when (position) {
                0 -> "Android"
                1 -> "iOS"
                2 -> "web"
                else -> throw IllegalStateException("wrong position: $position")
            }

    fun getTypeByName(name: String?): Int =
            when (name) {
                "Android", "iOS", "前端" -> 1
                "福利" -> 2
                else -> throw IllegalStateException("wrong type: $name")
            }

}