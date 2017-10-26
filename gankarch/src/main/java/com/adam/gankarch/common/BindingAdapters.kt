package com.adam.gankarch.common

import android.databinding.BindingAdapter
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import android.widget.ImageView


/**
 * 注意参数类型后边的问号,这是一个天坑
 * 例如bindImage方法的url,如果定义为 url: String 类型，将抛出
 * java.lang.IllegalArgumentException: Parameter specified as non-null is null:
 * method kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull, parameter url
 *
 * Created by yu on 2017/10/17.
 */
object BindingAdapters {

    @BindingAdapter("visibleGone")
    @JvmStatic
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun bindImage(icon: ImageView, url: String?) {
        if (!TextUtils.isEmpty(url)) {
            GlideApp.with(icon.context).load(url).into(icon)
        }
    }

    @BindingAdapter("imageUrlCenterCrop")
    @JvmStatic
    fun bindImageCenterCrop(icon: ImageView, url: String?) {
        if (!TextUtils.isEmpty(url)) {
            GlideApp.with(icon.context).load(url).centerCrop().into(icon)
        }
    }

    @BindingAdapter("html")
    @JvmStatic
    fun html(webView: WebView, html: String?) {
        if (!TextUtils.isEmpty(html)) {
            webView.loadDataWithBaseURL(null, html, "text/html", "utf8", null)
        }
    }

}