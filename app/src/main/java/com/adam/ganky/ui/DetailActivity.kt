package com.adam.ganky.ui

import android.graphics.Bitmap
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.adam.ganky.App
import com.adam.ganky.R
import com.adam.ganky.base.BaseMvpActivity
import com.adam.ganky.mvp.IDetail
import com.adam.ganky.mvp.presenter.DetailPresenter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.web_activity.*


class DetailActivity : BaseMvpActivity<DetailPresenter>(), IDetail.View {

    lateinit var url: String

    override fun getLayoutId(): Int = R.layout.web_activity

    override fun initView() {
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setHomeButtonEnabled(true)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        url = intent.getStringExtra("url")
        mPresenter.getGirl()

        initWebView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initWebView() {
        val webSettings = webview.getSettings()

        webSettings.setUseWideViewPort(true)
        webSettings.setLoadWithOverviewMode(true)

        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true) //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false) //隐藏原生的缩放控件

        webview.setWebViewClient(object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest?): Boolean {
                return true
            }
        })
        webview.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }
        })

        webview.loadUrl(url)
    }

    override fun injectComponent() {
        App.appComponent?.inject(this)
        mPresenter.attachView(this)
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
            webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            webview.clearHistory()
            (webview.getParent() as ViewGroup).removeView(webview)
            webview.destroy()
        }
        super.onDestroy()
    }
}
