package com.adam.ganky.ui.activity

import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
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

    lateinit var adapter: CollectionAdapter

    override fun getLayoutId() = R.layout.activity_collection

    override fun initView() {
        toolbar.title = "我的收藏"
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setHomeButtonEnabled(true)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        refreshLayout.setOnRefreshListener {
            mPresenter.refresh()
            adapter.setEnableLoadMore(true)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = CollectionAdapter(this, R.layout.item_collection, null) { adapter, view, position ->
            jump(DetailActivity::class.java, "entity", adapter.getItem(position) as GankEntity)
        }
        adapter.setEnableLoadMore(true)
        adapter.setOnLoadMoreListener({ mPresenter.loadMore() }, recyclerView)
        recyclerView.adapter = adapter
        adapter.disableLoadMoreIfNotFullPage()
        adapter.setOnItemLongClickListener { adapter, view, position ->

            AlertDialog.Builder(this@CollectionActivity)
                    .setMessage("你要把此条目移出收藏夹吗？")
                    .setNegativeButton("cancel", { dialog, which ->
                        dialog.dismiss()
                    })
                    .setPositiveButton("ok", { dialog, which ->
                        mPresenter.removeById((adapter.getItem(position) as GankEntity).id!!)
                        adapter.remove(position)
                    })
                    .create()
                    .show()
            true
        }
        adapter.setEmptyView(R.layout.layout_empty)

        refreshLayout.isRefreshing = true
        mPresenter.refresh()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
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

    override fun onError() {
        super.onError()
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
