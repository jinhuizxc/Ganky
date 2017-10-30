package com.adam.gankarch.ui.activity

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.adam.gankarch.R
import com.adam.gankarch.common.base.ArchBaseActivity
import com.adam.gankarch.common.extensions.jumpTo
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.databinding.ActivityCollectionBinding
import com.adam.gankarch.ui.adapter.CollectionAdapter
import com.adam.gankarch.viewmodel.CollectionViewModel
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * Created by yu on 2017/10/26.
 */
class CollectionActivity : ArchBaseActivity<ActivityCollectionBinding>() {

    private lateinit var mAdapter: CollectionAdapter
    private lateinit var mViewModel: CollectionViewModel

    override val layoutId: Int
        get() = R.layout.activity_collection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(mBinding.recyclerView) {
            layoutManager = LinearLayoutManager(this@CollectionActivity)
            setHasFixedSize(true)
            mAdapter = CollectionAdapter(null) { adapter, _, position ->
                jumpTo(DetailActivity::class.java, "entity", adapter.getItem(position) as GankEntity)
            }.apply {
                openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
                setEnableLoadMore(true)
                setOnLoadMoreListener({ mViewModel.loadMore() }, mBinding.recyclerView)
                setOnItemLongClickListener { adapter, _, position ->
                    AlertDialog.Builder(this@CollectionActivity)
                            .setMessage("你要把此条目移出收藏夹吗？")
                            .setNegativeButton("cancel", { dialog, _ ->
                                dialog.dismiss()
                            })
                            .setPositiveButton("ok", { _, _ ->
                                mViewModel.delete(adapter.getItem(position) as GankEntity)
                                adapter.remove(position)
                            })
                            .create()
                            .show()
                    true
                }
            }
            adapter = mAdapter
            mAdapter.setEmptyView(R.layout.layout_empty)
            mAdapter.disableLoadMoreIfNotFullPage()
        }

        mBinding.refreshLayout.setOnRefreshListener {
            mViewModel.refresh()
            mAdapter.setEnableLoadMore(true)
        }

        mViewModel = createViewModel(CollectionViewModel::class.java).apply {
            dataSet.observe(this@CollectionActivity, Observer {
                mAdapter.setNewData(it)
                mBinding.refreshLayout.isRefreshing = false
            })
            loadMoreData.observe(this@CollectionActivity, Observer {
                if (it != null && it.isNotEmpty()) {
                    mAdapter.addData(it)
                    mAdapter.loadMoreComplete()
                } else {
                    mAdapter.loadMoreEnd()
                }
            })
            deleteSuccess.observe(this@CollectionActivity, Observer { ToastUtils.showShort("已经移除收藏夹") })

            refresh()
        }

    }

}