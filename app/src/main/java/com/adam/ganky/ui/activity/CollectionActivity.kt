package com.adam.ganky.ui.activity

import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import com.adam.ganky.App
import com.adam.ganky.R
import com.adam.ganky.base.BaseMvpActivity
import com.adam.ganky.di.component.DaggerCollectionComponent
import com.adam.ganky.di.moudle.CollectionModule
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.jump
import com.adam.ganky.mvp.ICollection
import com.adam.ganky.mvp.presenter.CollectionPresenter
import com.adam.ganky.ui.adapter.CollectionAdapter
import com.adam.ganky.util.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_refresh_list.*

class CollectionActivity : BaseMvpActivity<CollectionPresenter>(), ICollection.View {

    private lateinit var adapter: CollectionAdapter

    override fun getLayoutId() = R.layout.activity_collection

    override fun initView() {
        with(toolbar) {
            title = "我的收藏"
            setSupportActionBar(this)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { onBackPressed() }
        }

        adapter = CollectionAdapter(this, R.layout.item_collection, null) { adapter, _, position ->
            jump(DetailActivity::class.java, "entity", adapter.getItem(position) as GankEntity)
        }.apply {
            setEnableLoadMore(true)
            setOnLoadMoreListener({ mPresenter.loadMore() }, recyclerView)
            disableLoadMoreIfNotFullPage()
            setOnItemLongClickListener { adapter, _, position ->

                AlertDialog.Builder(this@CollectionActivity)
                        .setMessage("你要把此条目移出收藏夹吗？")
                        .setNegativeButton("cancel", { dialog, _ ->
                            dialog.dismiss()
                        })
                        .setPositiveButton("ok", { _, _ ->
                            mPresenter.removeById((adapter.getItem(position) as GankEntity).id!!)
                            adapter.remove(position)
                        })
                        .create()
                        .show()
                true
            }
        }

        with(recyclerView) {
            layoutManager = LinearLayoutManager(this@CollectionActivity)
            setHasFixedSize(true)
            adapter = this@CollectionActivity.adapter
            this@CollectionActivity.adapter.setEmptyView(R.layout.layout_empty)
        }

        refreshLayout.setOnRefreshListener {
            mPresenter.refresh()
            adapter.setEnableLoadMore(true)
        }

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

    override fun onRemove() {
        ToastUtils.show("已经移出收藏夹")
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

}
