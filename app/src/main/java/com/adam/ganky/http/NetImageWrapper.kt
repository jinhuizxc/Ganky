package com.adam.ganky.http

import android.app.Activity
import android.support.v4.app.Fragment
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * 简单的封装，演示一个DSL风格的显示图片的类
 * Created by yu on 2017/9/26.
 */
class NetImageWrapper {
    var fragment: Fragment? = null
    var activity: Activity? = null
    var url: String? = null
    var imageView: ImageView? = null
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

    if (wrapper.fragment == null) {
        Glide.with(wrapper.activity).load(wrapper.url).centerCrop().into(wrapper.imageView)
    } else {
        Glide.with(wrapper.fragment).load(wrapper.url).centerCrop().into(wrapper.imageView)
    }

}
