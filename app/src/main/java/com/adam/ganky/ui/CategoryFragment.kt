package com.adam.ganky.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.adam.ganky.App
import com.adam.ganky.R
import com.adam.ganky.base.BaseMvpFragment
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.mvp.ICategory
import com.adam.ganky.mvp.presenter.CategoryPresenter
import com.adam.ganky.util.CategoryType
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_category.*

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

    override fun getLayoutId(): Int = R.layout.fragment_category
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
        App.appComponent?.inject(this)
        mPresenter.attachView(this)
    }
}

class CategoryAdapter(val context: Context, data: List<GankEntity>?)
    : BaseMultiItemQuickAdapter<GankEntity, BaseViewHolder>(data) {

    init {
        addItemType(CategoryType.ANDROID_IOS, R.layout.item_android)
        addItemType(CategoryType.GIRLS, R.layout.item_girls)
    }

    override fun convert(helper: BaseViewHolder, item: GankEntity) {
        when (helper.itemViewType) {
            CategoryType.ANDROID_IOS -> {
                helper.setText(R.id.tvDesc, item.desc)
                        .setText(R.id.tvAuthor, item.who)
                        .setText(R.id.tvDate, item.publishedAt)
            }
            else -> {
                val iv: RoundImageView = helper.getView(R.id.ivImage)
                Glide.with(context).load(item.url).centerCrop().into(iv)
            }
        }
    }
}