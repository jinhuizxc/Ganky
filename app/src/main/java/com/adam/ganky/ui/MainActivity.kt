package com.adam.ganky.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.adam.ganky.R
import com.adam.ganky.base.BaseActivity
import com.adam.ganky.base.BaseFragment
import com.adam.ganky.util.CategoryType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        mainPager.adapter = MainAdapter(supportFragmentManager)
        tabs.setupWithViewPager(mainPager)
    }
}

class MainAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val fmList: List<BaseFragment> by lazy {
        listOf(
                CategoryFragment.newInstance(CategoryType.ANDROID_STR),
                CategoryFragment.newInstance(CategoryType.IOS_STR),
                CategoryFragment.newInstance(CategoryType.GIRLS_STR)
        )
    }

    override fun getItem(position: Int): Fragment {
        return fmList.get(position)
    }

    override fun getCount(): Int {
        return fmList.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return CategoryType.getPageTitleByPosition(position)
    }
}