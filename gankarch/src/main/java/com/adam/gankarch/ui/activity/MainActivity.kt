package com.adam.gankarch.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.adam.gankarch.R
import com.adam.gankarch.common.base.BaseActivity
import com.adam.gankarch.common.base.BaseFragment
import com.adam.gankarch.common.extensions.jumpTo
import com.adam.gankarch.ui.adapter.HomeAdapter
import com.adam.gankarch.ui.fragment.CategoryFragment
import com.adam.gankarch.util.BackPressUtil
import com.adam.gankarch.util.TabType
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        with(toolbar) {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
        }

        with(mainPager) {
            this.adapter = HomeAdapter(supportFragmentManager)
            tabs.setupWithViewPager(this)
        }

        fab.setOnClickListener { jumpTo(CollectionActivity::class.java) }

    }

    override fun onBackPressed() {
        if (BackPressUtil.exitTwice())
            super.onBackPressed()
        else
            ToastUtils.showShort("再按一次退出")
    }
}