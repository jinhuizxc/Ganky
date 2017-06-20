package com.adam.ganky.ui

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adam.ganky.R
import com.adam.ganky.base.BaseMvpFragment
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.http.ApiService
import com.adam.ganky.http.RetrofitManager
import com.adam.ganky.mvp.CategoryPresenter
import com.adam.ganky.mvp.ICategory
import com.adam.ganky.rx.RxUtils
import com.adam.ganky.util.CategoryType
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments.getString("type")
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        ui = CategoryFragmentUI()
        val view = ui.createView(AnkoContext.create(activity, this))
        adapter = CategoryAdapter(null)

        ui.rv.adapter = adapter
        ui.srl.onRefresh { mPresenter.refresh(type) }
        return view
    }

    override fun initData() {
        Log.e("qwr", "type = $type initData...")

        // test todo
        RetrofitManager.instance.createService(ApiService::class.java)
                .gank("Android", "10", "1")
                .compose(RxUtils.parseResult())
                .compose(RxUtils.apiTransformer(this))
                .subscribe(object : Observer<List<GankEntity>> {
                    override fun onNext(data: List<GankEntity>?) {
                        adapter.setNewData(data)
                    }

                    override fun onError(e: Throwable?) {
                    }

                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }
                })

    }

    override fun onRefresh(data: List<GankEntity>) {
        adapter.setNewData(data)
        ui.srl.isRefreshing = false
    }

    override fun onLoadMore(data: List<GankEntity>) {
    }

    override fun onNoMore() {
    }

    override fun injectComponent() {
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

class CategoryAdapter(data: List<GankEntity>?) : BaseMultiItemQuickAdapter<GankEntity, BaseViewHolder>(data) {
    init {
        addItemType(CategoryType.ANDROID, R.layout.item_android)
        addItemType(CategoryType.IOS, R.layout.item_android)
        addItemType(CategoryType.GRILS, R.layout.item_android)
    }

    override fun convert(helper: BaseViewHolder?, item: GankEntity?) {
        when (helper?.itemViewType) {
            CategoryType.ANDROID -> {
                helper.setText(R.id.tvDesc, item?.desc)
                        ?.setText(R.id.tvAuthor, item?.who)
                        ?.setText(R.id.tvDate, item?.publishedAt)
            }
            CategoryType.IOS -> {
                helper.setText(R.id.tvDesc, item?.desc)
                        ?.setText(R.id.tvAuthor, item?.who)
                        ?.setText(R.id.tvDate, item?.publishedAt)
            }
            CategoryType.GRILS -> {

            }
        }
    }
}