package com.adam.gankarch.ui.activity

import android.os.Bundle
import com.adam.gankarch.R
import com.adam.gankarch.common.base.BaseActivity
import com.adam.gankarch.common.extensions.jumpTo
import com.adam.gankarch.ui.adapter.HomeAdapter
import com.adam.gankarch.util.BackPressUtil
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { jumpTo(CollectionActivity::class.java) }
        with(mainPager) {
            this.adapter = HomeAdapter(supportFragmentManager)
            tabs.setupWithViewPager(this)
        }
    }

    override fun onBackPressed() {
        if (BackPressUtil.exitTwice())
            super.onBackPressed()
        else
            ToastUtils.showShort("再按一次退出")
    }
}