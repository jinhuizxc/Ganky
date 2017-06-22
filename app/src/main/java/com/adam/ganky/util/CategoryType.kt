package com.adam.ganky.util

/**
 * 列表的分类
 * Created by yu on 2017/6/20.
 */
object CategoryType {

    const val ANDROID_IOS = 1
    const val GIRLS = 2

    const val ANDROID_STR = "Android"
    const val IOS_STR = "iOS"
    const val GIRLS_STR = "福利"

    fun getPageTitleByPosition(position: Int): String {
        return when (position) {
            0 -> ANDROID_STR
            1 -> IOS_STR
            2 -> GIRLS_STR
            else -> ""
        }
    }

    fun getTypeByName(name: String?): Int {
        return if (name.equals(ANDROID_STR) || name.equals(IOS_STR)) {
            ANDROID_IOS
        } else {
            GIRLS
        }
    }
}