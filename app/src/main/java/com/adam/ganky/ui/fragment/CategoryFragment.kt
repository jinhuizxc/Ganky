package com.adam.ganky.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.util.Log
import com.adam.ganky.App
import com.adam.ganky.R
import com.adam.ganky.base.BaseMvpFragment
import com.adam.ganky.di.component.DaggerCategoryComponent
import com.adam.ganky.di.moudle.CategoryModule
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.jump
import com.adam.ganky.mvp.ICategory
import com.adam.ganky.mvp.presenter.CategoryPresenter
import com.adam.ganky.ui.activity.DetailActivity
import com.adam.ganky.ui.adapter.CategoryAdapter
import com.adam.ganky.util.CategoryType
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.layout_refresh_list.*

/**
 * tab
 * Created by yu on 2017/6/19.
 */
class CategoryFragment : BaseMvpFragment<CategoryPresenter>(), ICategory.View {

    companion object {
        fun newInstance(type: String): CategoryFragment {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            bundle.putString("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    lateinit var type: String
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments.getString("type")
    }

    override fun getLayoutId(): Int = R.layout.layout_refresh_list
    override fun initView() {

        adapter = CategoryAdapter(this@CategoryFragment, null) { adapter, _, position ->
            if (type != CategoryType.GIRLS_STR)
                jump(DetailActivity::class.java, "entity", adapter.getItem(position) as GankEntity)
        }.apply {
            // apply 调用对象的方法（内部隐含this），返回对象自己
            openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
            setEnableLoadMore(true)
            setOnLoadMoreListener({ mPresenter.loadMore(type) }, recyclerView)
        }.let {
            // let 内部隐含it，表示调用的对象，返回值为最后一行
            Log.e("tag", "init adapter success")
            it // 这里没有it返回的就是上一行，即Unit
        }

        // with 调用参数对象的方法（内部隐含this）,返回闭包
        with(recyclerView) {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = this@CategoryFragment.adapter

            if (type == CategoryType.GIRLS_STR)
                PagerSnapHelper().attachToRecyclerView(this)
        }

        refreshLayout.setOnRefreshListener {
            mPresenter.refresh(type)
            adapter.setEnableLoadMore(true)
        }
    }

    override fun initData() {
        refreshLayout.isRefreshing = true
        mPresenter.refresh(type)
    }

    override fun onRefresh(data: List<GankEntity>) {
        adapter.setNewData(data)
        refreshLayout.isRefreshing = false
    }

    override fun onLoadMore(data: List<GankEntity>, hasMore: Boolean) {
        with(adapter) {
            addData(data)
            if (hasMore)
                loadMoreComplete()
            else
                loadMoreEnd()
        }
    }

    override fun hideLoading() {
        resetLoading()
    }

    /**
     * 加载过程中发送错误时，重置刷新头或者footer的状态
     */
    private fun resetLoading() {
        refreshLayout.isRefreshing = false
        adapter.loadMoreComplete()
    }

    override fun injectComponent() {
        DaggerCategoryComponent.builder()
                .appComponent(App.appComponent)
                .categoryModule(CategoryModule(this))
                .build()
                .inject(this)
    }
}