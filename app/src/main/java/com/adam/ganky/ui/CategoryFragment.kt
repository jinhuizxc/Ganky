package com.adam.ganky.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.adam.ganky.App
import com.adam.ganky.R
import com.adam.ganky.base.BaseMvpFragment
import com.adam.ganky.di.component.DaggerCategoryComponent
import com.adam.ganky.di.moudle.CategoryModule
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.mvp.ICategory
import com.adam.ganky.mvp.presenter.CategoryPresenter
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
        refreshLayout.setOnRefreshListener {
            mPresenter.refresh(type)
            adapter.setEnableLoadMore(true)
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        adapter = CategoryAdapter(activity, null)
        adapter.setEnableLoadMore(true)
        adapter.setOnLoadMoreListener({ mPresenter.loadMore(type) }, recyclerView)
        adapter.setOnItemClickListener { adapter, view, position ->
            val url = (adapter.getItem(position) as GankEntity).url
            startActivity(Intent(activity, DetailActivity::class.java).putExtra("url", url))
        }
        recyclerView.adapter = adapter
    }

    override fun initData() {
        mPresenter.refresh(type)
    }

    override fun onRefresh(data: List<GankEntity>) {
        adapter.setNewData(data)
        refreshLayout.isRefreshing = false
    }

    override fun onLoadMore(data: List<GankEntity>) {
        adapter.addData(data)
        adapter.loadMoreComplete()
    }

    override fun onNoMore() {
        adapter.loadMoreEnd()
    }

    override fun injectComponent() {
        DaggerCategoryComponent.builder()
                .appComponent(App.appComponent)
                .categoryModule(CategoryModule(this))
                .build()
                .inject(this)
    }
}