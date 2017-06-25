package com.adam.ganky.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
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
    lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments.getString("type")
    }

    override fun getLayoutId(): Int = R.layout.layout_refresh_list
    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        adapter = CategoryAdapter(activity, null) { adapter, view, position ->
            if (type != CategoryType.GIRLS_STR)
                jump(DetailActivity::class.java, "entity", adapter.getItem(position) as GankEntity)
        }
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
        adapter.setEnableLoadMore(true)
        adapter.setOnLoadMoreListener({ mPresenter.loadMore(type) }, recyclerView)

        recyclerView.adapter = adapter

        if (type == CategoryType.GIRLS_STR)
            PagerSnapHelper().attachToRecyclerView(recyclerView)

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
        adapter.addData(data)
        if (hasMore)
            adapter.loadMoreComplete()
        else
            adapter.loadMoreEnd()
    }

    override fun onError() {
        super.onError()
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