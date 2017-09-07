package com.adam.ganky.mvp.presenter

import com.adam.ganky.base.BasePresenter
import com.adam.ganky.http.ErrorHandler
import com.adam.ganky.mvp.ICategory
import com.adam.ganky.mvp.repository.CategoryRepository
import com.adam.ganky.rx.RxUtils
import javax.inject.Inject

/**
 * Created by yu on 2017/6/20.
 */
class CategoryPresenter
@Inject constructor(val view: ICategory.View, val repository: CategoryRepository)
    : BasePresenter<ICategory.View>(view), ICategory.Presenter {

    private var pageNum = 1

    override fun refresh(type: String, pageSize: Int) {
        pageNum = 1
        load(type, pageSize, false)
    }

    override fun loadMore(type: String, pageSize: Int) {
        pageNum++
        load(type, pageSize, true)
    }

    private fun load(type: String, pageSize: Int, isLoadmore: Boolean) {
        repository.loadData(type, pageSize, pageNum)
                .compose(RxUtils.defaultTransformer(mView))
                .subscribe({
                    if (isLoadmore)
                        mView?.onLoadMore(it, it.size == pageSize)
                    else
                        mView?.onRefresh(it)
                }, { ErrorHandler.handleException(it) })
                .apply { addDisposable(this) }
    }
}