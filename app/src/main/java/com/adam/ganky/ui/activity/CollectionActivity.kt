package com.adam.ganky.ui.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.adam.ganky.App
import com.adam.ganky.R
import com.adam.ganky.base.BaseMvpActivity
import com.adam.ganky.di.component.DaggerCollectionComponent
import com.adam.ganky.di.moudle.CollectionModule
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.mvp.ICollection
import com.adam.ganky.mvp.presenter.CollectionPresenter
import com.adam.ganky.ui.adapter.CategoryAdapter
import kotlinx.android.synthetic.main.layout_refresh_list.*

class CollectionActivity : BaseMvpActivity<CollectionPresenter>(), ICollection.View {

    lateinit var adapter: CategoryAdapter

    override fun getLayoutId() = R.layout.layout_refresh_list

    override fun initView() {
        refreshLayout.setOnRefreshListener {
            mPresenter.refresh()
            adapter.setEnableLoadMore(true)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = CategoryAdapter(this, null){ adapter, view, position ->
            val url = (adapter.getItem(position) as GankEntity).url
            startActivity(Intent(this@CollectionActivity, DetailActivity::class.java).putExtra("url", url))
        }
        adapter.setEnableLoadMore(true)
        adapter.setOnLoadMoreListener({ mPresenter.loadMore() }, recyclerView)
        recyclerView.adapter = adapter

        refreshLayout.isRefreshing = true
        mPresenter.refresh()
    }

    override fun injectComponent() {
        DaggerCollectionComponent.builder()
                .appComponent(App.appComponent)
                .collectionModule(CollectionModule(this))
                .build()
                .inject(this)
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
}
