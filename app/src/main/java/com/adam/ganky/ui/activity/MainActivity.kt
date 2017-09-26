package com.adam.ganky.ui.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.adam.ganky.R
import com.adam.ganky.base.BaseActivity
import com.adam.ganky.base.BaseFragment
import com.adam.ganky.jump
import com.adam.ganky.ui.fragment.CategoryFragment
import com.adam.ganky.util.CategoryType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        toolbar.apply {
            title = getString(R.string.app_name)
        }.let {
            setSupportActionBar(it)
        }

        with(mainPager) {
            this.adapter = MainAdapter(supportFragmentManager)
            tabs.setupWithViewPager(this)
        }

        fab.setOnClickListener { jump(CollectionActivity::class.java) }

    }

}

class MainAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val fmList: List<BaseFragment> by lazy {
        listOf(
                CategoryFragment.newInstance(CategoryType.ANDROID_STR),
                CategoryFragment.newInstance(CategoryType.IOS_STR),
                CategoryFragment.newInstance(CategoryType.GIRLS_STR)
        )
    }

    override fun getItem(position: Int): Fragment = fmList[position]

    override fun getCount(): Int = fmList.size

    override fun getPageTitle(position: Int): CharSequence = CategoryType.getPageTitleByPosition(position)
}