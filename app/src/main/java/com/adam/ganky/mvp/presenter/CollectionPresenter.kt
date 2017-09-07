package com.adam.ganky.mvp.presenter

import com.adam.ganky.base.BasePresenter
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.http.ErrorHandler
import com.adam.ganky.mvp.ICollection
import com.adam.ganky.mvp.repository.CollectionRepository
import com.adam.ganky.rx.RxUtils
import javax.inject.Inject

/**
 * @author yu
 * Create on 2017/6/22.
 */
class CollectionPresenter
@Inject constructor(val view: ICollection.View, val repository: CollectionRepository)
    : BasePresenter<ICollection.View>(view), ICollection.Presenter {

    private var pageNum = 0

    override fun refresh(pageSize: Int) {
        pageNum = 0
        load(pageSize, false)
    }

    override fun loadMore(pageSize: Int) {
        pageNum++
        load(pageSize, true)
    }

    private fun load(pageSize: Int, isLoadmore: Boolean) {
        repository.getCollections(pageNum, pageSize)
                .compose(RxUtils.defaultTransformer(mView))
                .subscribe({
                    if (isLoadmore) mView?.onLoadMore(it, it.size == pageSize)
                    else mView?.onRefresh(it)
                }, { ErrorHandler.handleException(it) })
                .apply { addDisposable(this) }
    }

    override fun remove(entity: GankEntity) {
        repository.remove(entity)
        mView?.onRemove()
    }
}