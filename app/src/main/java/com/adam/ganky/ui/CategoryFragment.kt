package com.adam.ganky.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adam.ganky.base.LazyFragment
import org.jetbrains.anko.*

/**
 * @author yu
 * Create on 2017/6/19.
 */
class CategoryFragment : LazyFragment() {

    companion object {
        fun newInstance(type: String): CategoryFragment {
            val fragment = CategoryFragment()
            fragment.arguments = bundleOf(Pair("type", type))
            return fragment
        }
    }

    lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments.get("type") as String
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return CategoryFragmentUI().createView(AnkoContextImpl(activity, this, false))
    }

    override fun initData() {
        Log.e("qwr", "type = $type initData...")
    }
}

class CategoryFragmentUI : AnkoComponent<CategoryFragment> {
    override fun createView(ui: AnkoContext<CategoryFragment>) = with(ui) {
        verticalLayout {
            textView("haha") {
                textSize = sp(14).toFloat()
                backgroundColor = Color.parseColor("#cccccc")
            }
        }
    }
}