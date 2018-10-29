package com.adam.ganky.http

import android.app.Activity
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.widget.ImageView
import com.adam.ganky.GlideApp
import com.adam.ganky.GlideRequest

/**
 * 简单的封装，演示一个DSL风格的显示图片的类
 * Created by yu on 2017/9/26.
 */
class NetImageWrapper {
    var fragment: Fragment? = null
    var activity: Activity? = null

    var url: String? = null
    var imageView: ImageView? = null
    var scaleType: ScaleType? = ScaleType.CENTER_CROP
    var errorId: Int = 0
    var placeHolder: Int = 0

    fun execute() {
        if (imageView == null || url == null) {
            throw NullPointerException("imageView or url is null")
        }
        if (fragment == null && activity == null) {
            throw NullPointerException("fragment 和 activity 至少要有一个啊，兄弟")
        }
        var glideRequest: GlideRequest<Drawable> = if (fragment == null) {
            GlideApp.with(activity).load(url)
        } else {
            GlideApp.with(fragment).load(url)
        }

        if (errorId != 0) {
            glideRequest = glideRequest.error(errorId)
        }
        if (placeHolder != 0) {
            glideRequest = glideRequest.placeholder(placeHolder)
        }

        when (scaleType) {
            ScaleType.CENTER_CROP -> glideRequest.centerCrop().into(imageView)
            ScaleType.CENTER_INSIDE -> glideRequest.centerInside().into(imageView)
            ScaleType.FIT_CENTER -> glideRequest.fitCenter().into(imageView)
        }

    }
}

enum class ScaleType {
    CENTER_CROP,
    CENTER_INSIDE,
    FIT_CENTER,
}

fun displayImage(block: NetImageWrapper.() -> Unit) {
    NetImageWrapper().apply(block).execute()
}

