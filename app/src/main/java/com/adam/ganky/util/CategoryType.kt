package com.adam.ganky.util

import android.util.SparseArray

/**
 * 列表的分类
 * Created by yu on 2017/6/20.
 */
object CategoryType {

    const val ANDROID = 0
    const val IOS = 1
    const val GRILS = 2

    const val ANDROID_STR = "Android"
    const val IOS_STR = "iOS"
    const val GRILS_STR = "福利"

    private var spa: SparseArray<String> = SparseArray<String>().apply {
        this.put(ANDROID, ANDROID_STR)
        this.put(IOS, IOS_STR)
        this.put(GRILS, GRILS_STR)
    }

    fun getTypeByName(name: String?): Int = spa.indexOfValue(name)
}