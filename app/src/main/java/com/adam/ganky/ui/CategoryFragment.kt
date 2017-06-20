package com.adam.ganky.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adam.ganky.App
import com.adam.ganky.R
import com.adam.ganky.base.BaseMvpFragment
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.mvp.CategoryPresenter
import com.adam.ganky.mvp.ICategory
import com.adam.ganky.util.CategoryType
import com.yl.library.common.ItemViewDelegate
import com.yl.library.common.ViewHolder
import com.yl.library.recycler.MultiTypeRvAdapter
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * @author yu
 * Create on 2017/6/19.
 */
class CategoryFragment : BaseMvpFragment<CategoryPresenter>(), ICategory.View {

    companion object {
        fun newInstance(type: String): CategoryFragment {
            val fragment = CategoryFragment()
            fragment.arguments = bundleOf(Pair("type", type))
            return fragment
        }
    }

    lateinit var type: String
    lateinit var ui: CategoryFragmentUI
    lateinit var adapter: CategoryAdapter
    val dataList: MutableList<GankEntity> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments.getString("type")
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        ui = CategoryFragmentUI()
        val view = ui.createView(AnkoContext.create(activity, this))
        adapter = CategoryAdapter(activity, dataList)

        ui.rv.adapter = adapter
        ui.srl.onRefresh { mPresenter.refresh(type) }
        return view
    }

    override fun initData() {
        mPresenter.refresh(type)
    }

    override fun onRefresh(data: List<GankEntity>) {
        dataList.clear()
        dataList.addAll(data)
        adapter.notifyDataSetChanged()
        ui.srl.isRefreshing = false
    }

    override fun onLoadMore(data: List<GankEntity>) {
        dataList.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onNoMore() {
    }

    override fun injectComponent() {
        App.apiComponent?.inject(this)
        mPresenter.mView = this
    }
}

class CategoryFragmentUI : AnkoComponent<CategoryFragment> {

    lateinit var rv: RecyclerView
    lateinit var srl: SwipeRefreshLayout

    override fun createView(ui: AnkoContext<CategoryFragment>) = with(ui) {
        srl = swipeRefreshLayout {
            rv = recyclerView {
                layoutManager = LinearLayoutManager(ui.ctx)
            }
        }
        srl
    }
}

class CategoryAdapter(context: Context, data: List<GankEntity>?) : MultiTypeRvAdapter<GankEntity>(context, data) {

    init {
        addItemViewDelegate(object : ItemViewDelegate<GankEntity> {
            override fun getItemViewLayoutId(): Int = R.layout.item_android

            override fun bindData(holder: ViewHolder?, entity: GankEntity?, p2: Int) {
                holder?.setText(R.id.tvDesc, entity?.desc)
                        ?.setText(R.id.tvAuthor, entity?.who)
                        ?.setText(R.id.tvDate, entity?.publishedAt)
            }

            override fun isForThisViewType(entity: GankEntity?, p1: Int): Boolean {
                return entity?.type.equals(CategoryType.ANDROID_STR, true)
                        || entity?.type.equals(CategoryType.IOS_STR, true)
            }
        })
//                .addItemViewDelegate(object : ItemViewDelegate<GankEntity> {
//            override fun getItemViewLayoutId(): Int {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun bindData(p0: ViewHolder?, p1: GankEntity?, p2: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun isForThisViewType(p0: GankEntity?, p1: Int): Boolean {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        })
    }

}