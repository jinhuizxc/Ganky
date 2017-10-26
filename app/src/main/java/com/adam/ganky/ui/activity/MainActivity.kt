package com.adam.ganky.ui.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.adam.ganky.R
import com.adam.ganky.base.BaseActivity
import com.adam.ganky.base.BaseFragment
import com.adam.ganky.jump
import com.adam.ganky.ui.fragment.CategoryFragment
import com.adam.ganky.util.AppUtils
import com.adam.ganky.util.CategoryType
import com.adam.ganky.util.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.apply {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
        }

        with(mainPager) {
            this.adapter = HomeAdapter(supportFragmentManager)
            tabs.setupWithViewPager(this)
        }

        fab.setOnClickListener { jump(CollectionActivity::class.java) }

    }

    override fun onBackPressed() {
        if (AppUtils.exitTwice())
            super.onBackPressed()
        else
            ToastUtils.show("再按一次退出")
    }

}

class HomeAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val fmList: List<BaseFragment> by lazy {
        listOf(
                CategoryFragment.newInstance(CategoryType.ANDROID.nameStr),
                CategoryFragment.newInstance(CategoryType.IOS.nameStr),
                CategoryFragment.newInstance(CategoryType.GIRLS.nameStr)
        )
    }

    override fun getItem(position: Int): Fragment = fmList[position]

    override fun getCount(): Int = fmList.size

    override fun getPageTitle(position: Int): CharSequence = CategoryType.getPageTitleByPosition(position)
}