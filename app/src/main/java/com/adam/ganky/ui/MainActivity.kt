package com.adam.ganky.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.adam.ganky.base.BaseActivity
import com.adam.ganky.base.BaseFragment
import com.adam.ganky.util.CategoryType
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.support.v4.viewPager

class MainActivity : BaseActivity() {
    override fun initView() {
        MainActivityUI().setContentView(this)
    }
}

class MainActivityUI : AnkoComponent<MainActivity> {
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        viewPager {
            id = 123
            adapter = MainAdapter(ui.owner.supportFragmentManager)
        }
    }
}

class MainAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val fmList: List<BaseFragment> by lazy {
        listOf(
                CategoryFragment.newInstance(CategoryType.ANDROID_STR),
                CategoryFragment.newInstance(CategoryType.IOS_STR),
                CategoryFragment.newInstance(CategoryType.GRILS_STR)
        )
    }

    override fun getItem(position: Int): Fragment {
        return fmList.get(position)
    }

    override fun getCount(): Int {
        return fmList.size
    }
}