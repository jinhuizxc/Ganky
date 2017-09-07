package com.adam.ganky.ui.activity

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.adam.ganky.App
import com.adam.ganky.R
import com.adam.ganky.base.BaseMvpActivity
import com.adam.ganky.di.component.DaggerDetailComponent
import com.adam.ganky.di.moudle.DetailModule
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.mvp.IDetail
import com.adam.ganky.mvp.presenter.DetailPresenter
import com.adam.ganky.util.ToastUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.web_activity.*
import kotlin.properties.Delegates


class DetailActivity : BaseMvpActivity<DetailPresenter>(), IDetail.View {

    lateinit var entity: GankEntity

    private var isFavorite by Delegates.observable(false) { _, _, new ->
        fab.backgroundTintList = if (new) {
            ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
        } else {
            ColorStateList.valueOf(resources.getColor(R.color.C4))
        }
    }

    override fun getLayoutId(): Int = R.layout.web_activity

    override fun initView() {
        with(toolbar) {
            title = getString(R.string.app_name)
            setSupportActionBar(toolbar)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { onBackPressed() }
        }

        entity = intent.getSerializableExtra("entity") as GankEntity
        mPresenter.getGirl()

        fab.setOnClickListener {
            if (isFavorite) {
                mPresenter.remove(entity)
                ToastUtils.show("已移除收藏夹")
            } else {
                mPresenter.addToFavorites(entity)
                ToastUtils.show("已添加到收藏夹")
            }
        }

        mPresenter.isFavorite(entity.id!!)

        initWebView()
    }

    private fun initWebView() {
        webview.apply {
            settings.let {
                it.useWideViewPort = true
                it.loadWithOverviewMode = true

                it.setSupportZoom(true)
                it.builtInZoomControls = true
                it.displayZoomControls = false
            }
            setWebViewClient(object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                }

                override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest?): Boolean = true
            })
            setWebChromeClient(object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                }
            })
        }.loadUrl(entity.url)

    }

    override fun injectComponent() {
        DaggerDetailComponent.builder()
                .appComponent(App.appComponent)
                .detailModule(DetailModule(this))
                .build()
                .inject(this)
    }

    override fun onFavoriteChange(isFavorite: Boolean) {
        this.isFavorite = isFavorite// 通过委托属性重置FloatingActionButton状态
    }

    override fun showGirl(url: String) {
        Glide.with(this).load(url).fitCenter().into(imageView)
    }

    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        if (webview != null) {
            (webview.parent as ViewGroup).removeView(webview)
            webview.destroy()
        }
        super.onDestroy()
    }
}
