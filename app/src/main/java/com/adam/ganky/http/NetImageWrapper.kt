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
}

enum class ScaleType {
    CENTER_CROP,
    CENTER_INSIDE,
    FIT_CENTER,
}

fun displayImage(block: NetImageWrapper.() -> Unit) {
    val wrapper = NetImageWrapper()
    wrapper.block()
    execute(wrapper)
}

private fun execute(wrapper: NetImageWrapper) {
    if (wrapper.imageView == null || wrapper.url == null) {
        throw NullPointerException("imageView or url is null")
    }
    if (wrapper.fragment == null && wrapper.activity == null) {
        throw NullPointerException("fragment 和 activity 至少要有一个啊，兄弟")
    }
    var glideRequest: GlideRequest<Drawable> = if (wrapper.fragment == null) {
        GlideApp.with(wrapper.activity).load(wrapper.url)
    } else {
        GlideApp.with(wrapper.fragment).load(wrapper.url)
    }

    if (wrapper.errorId != 0) {
        glideRequest = glideRequest.error(wrapper.errorId)
    }
    if (wrapper.placeHolder != 0) {
        glideRequest = glideRequest.placeholder(wrapper.placeHolder)
    }

    when (wrapper.scaleType) {
        ScaleType.CENTER_CROP -> glideRequest.centerCrop().into(wrapper.imageView)
        ScaleType.CENTER_INSIDE -> glideRequest.centerInside().into(wrapper.imageView)
        ScaleType.FIT_CENTER -> glideRequest.fitCenter().into(wrapper.imageView)
    }

}
