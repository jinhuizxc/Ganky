package com.adam.ganky.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.adam.ganky.base.LazyFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.viewPager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        MainActivityUI().setContentView(this)

    }

    fun doLogin(name: String) {

    }
}

class MainActivityUI : AnkoComponent<MainActivity> {
    private val customStyle = { v: Any ->
        when (v) {
            is Button -> v.textSize = 16f
            is EditText -> v.textSize = 18f
        }
    }

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        verticalLayout {
            //            padding = dip(15)
//            val name = editText() {
//                hint = "请输入name"
//            }
//            button("Say Hello") {
//                backgroundColor = 0xffcccc
//                onClick {
//                    ui.owner.doLogin(name.text.toString().trim())
//                }
//            }.lparams {
//                width = matchParent
//                topMargin = dip(10)
//                horizontalMargin = dip(5)
//            }
            viewPager {
                id = 123
                adapter = MainAdapter(ui.owner.supportFragmentManager)
            }.lparams {
                width = matchParent
                height = matchParent
            }

        }.applyRecursively(customStyle)
    }
}

class MainAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val fmList: List<LazyFragment>

    init {
        fmList = listOf(CategoryFragment.newInstance("iOS"),
                CategoryFragment.newInstance("Android"),
                CategoryFragment.newInstance("Girls"))
    }

    override fun getItem(position: Int): Fragment {
        return fmList.get(position)
    }

    override fun getCount(): Int {
        return fmList.size
    }
}