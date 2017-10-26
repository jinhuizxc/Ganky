package com.adam.gankarch.util

/**
 * Created by yu on 2017/10/26.
 */
object BackPressUtil {
    /**
     * 第一次和第二次的退出间隔时间
     */
    private val EXIT_TWICE_INTERVAL: Long = 2000
    private var mExitTime: Long = 0

    /**
     * 第二次按退出则返回true,否则返回false
     */
    fun exitTwice(): Boolean {
        val newExitTime = System.currentTimeMillis()
        return if (newExitTime - mExitTime > EXIT_TWICE_INTERVAL) {
            mExitTime = newExitTime
            false
        } else {
            true
        }
    }
}