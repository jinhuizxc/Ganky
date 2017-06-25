package com.adam.ganky.util

import android.app.Activity
import android.content.res.Resources
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.Window

import java.lang.reflect.Field

/**
 * 屏幕显示相关的工具类
 */
object ScreenUtils {

    /**
     * 屏幕宽度
     */
    fun width(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    /**
     * 屏幕高度
     */
    fun height(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    /**
     * dp转px
     */
    fun dp2px(dp: Float): Int {
        return (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    }

    /**
     * 将px转换成dp
     */
    fun px2dp(px: Float): Int {
        return (px / Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    }

    /**
     * 获取状态栏高度
     * 注: 该方法在onCreate中获取值为0
     */
    fun statusBarHeight(activity: Activity): Int {
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        return frame.top
    }

    /**
     * 获取状态栏高度
     * 注: 该方法在onCreate中获取值为0
     */
    fun statusBarHeight(resources: Resources): Int {
        var c: Class<*>? = null
        var obj: Any? = null
        var field: Field? = null
        var x = 0
        var statusBarHeight = 0
        try {
            c = Class.forName("com.android.internal.R\$dimen")
            obj = c!!.newInstance()
            field = c.getField("status_bar_height")
            x = Integer.parseInt(field!!.get(obj).toString())
            statusBarHeight = resources.getDimensionPixelSize(x)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return statusBarHeight
    }

    /**
     * 获取标题栏高度
     */
    fun titleBarHeight(activity: Activity): Int {
        val contentTop = activity.window.findViewById(Window.ID_ANDROID_CONTENT).top
        val titleBarHeight = contentTop - statusBarHeight(activity)
        return titleBarHeight
    }

}
