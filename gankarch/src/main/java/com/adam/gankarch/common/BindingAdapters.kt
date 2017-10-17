package com.adam.gankarch.common

import android.databinding.BindingAdapter
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import android.widget.ImageView


/**
 * Created by yu on 2017/10/17.
 */
class BindingAdapters {
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @BindingAdapter("imageUrl")
    fun bindImage(icon: ImageView, url: String) {
        if (!TextUtils.isEmpty(url)) {
            GlideApp.with(icon.context).load(url).into(icon)
        }
    }

    @BindingAdapter("html")
    fun html(webView: WebView, html: String) {
        webView.loadDataWithBaseURL(null, html, "text/html", "utf8", null)
    }

}