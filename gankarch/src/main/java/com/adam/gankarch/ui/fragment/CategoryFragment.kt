package com.adam.gankarch.ui.fragment

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.View
import com.adam.gankarch.R
import com.adam.gankarch.common.base.ArchBaseFragment
import com.adam.gankarch.common.extensions.jumpTo
import com.adam.gankarch.common.extensions.withArgument
import com.adam.gankarch.data.entity.GankEntity
import com.adam.gankarch.databinding.FragmentCategoryBinding
import com.adam.gankarch.ui.activity.DetailActivity
import com.adam.gankarch.ui.adapter.CategoryAdapter
import com.adam.gankarch.viewmodel.CategoryViewModel
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * Created by yu on 2017/10/23.
 */
class CategoryFragment : ArchBaseFragment<FragmentCategoryBinding>() {

    companion object {
        fun newInstance(type: String): CategoryFragment =
                CategoryFragment().withArgument("type", type)
    }

    private lateinit var type: String
    private lateinit var mAdapter: CategoryAdapter
    private lateinit var mViewModel: CategoryViewModel

    override fun getLayoutId(): Int = R.layout.fragment_category

    override fun initView(view: View?) {

        type = arguments.getString("type")

        mAdapter = CategoryAdapter(null) { adapter, _, position ->
            if (type != "福利")
                jumpTo(DetailActivity::class.java, "entity", adapter.getItem(position) as GankEntity)
        }.apply {
            openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
            setEnableLoadMore(true)
            setOnLoadMoreListener({ mViewModel.loadMore(type) }, mBinding.recyclerView)
        }

        with(mBinding.recyclerView) {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = mAdapter

            if (type == "福利")
                PagerSnapHelper().attachToRecyclerView(this)
        }

        mBinding.refreshLayout.setOnRefreshListener {
            mViewModel.refresh(type)
            mAdapter.setEnableLoadMore(true)
        }
    }

    override fun initData() {
        mViewModel = getViewModel(CategoryViewModel::class.java)
        mViewModel.dataSet.observe(this, Observer {
            mAdapter.setNewData(it)
            mBinding.refreshLayout.isRefreshing = false
        })
        mViewModel.loadMoreData.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                mAdapter.addData(it)
                mAdapter.loadMoreComplete()
            } else {
                mAdapter.loadMoreEnd()
            }
        })
        mViewModel.refresh(type)
    }
}