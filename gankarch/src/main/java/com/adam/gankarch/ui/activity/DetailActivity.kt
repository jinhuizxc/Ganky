package com.adam.gankarch.ui.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.adam.gankarch.R
import com.adam.gankarch.common.base.ArchBaseActivity
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.databinding.ActivityDetailBinding
import com.adam.gankarch.viewmodel.DetailViewModel

/**
 * Created by yu on 2017/10/26.
 */
class DetailActivity : ArchBaseActivity<ActivityDetailBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(mBinding.toolbar) {
            setSupportActionBar(this)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { onBackPressed() }
        }

        with(mBinding.webview) {
            settings.apply {
                useWideViewPort = true
                loadWithOverviewMode = true

                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false
            }
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest?): Boolean = true
            }
        }

        initData()

    }

    private fun initData() {
        val viewModel = createViewModel(DetailViewModel::class.java)
        viewModel.girl.observe(this, Observer { mBinding.girl = it })

        mBinding.vm = viewModel

        val entity = intent.getSerializableExtra("entity") as GankEntity
        mBinding.entity = entity


    }

}