package com.adam.ganky.mvp.presenter

import com.adam.ganky.base.BasePresenter
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.http.ApiSubscriber
import com.adam.ganky.mvp.ICollection
import com.adam.ganky.mvp.repository.CollectionRepository
import com.adam.ganky.rx.RxUtils
import javax.inject.Inject

/**
 * @author yu
 * Create on 2017/6/22.
 */
class CollectionPresenter
@Inject constructor(view: ICollection.View, val repository: CollectionRepository)
    : BasePresenter<ICollection.View>(view), ICollection.Presenter {

    var pageNum = 0

    override fun create() {

    }

    override fun refresh(pageSize: Int) {
        pageNum = 0
        repository.getCollections(pageNum, pageSize)
                .compose(RxUtils.bindToLifecycle(mView))
                .compose(RxUtils.ioToMain())
                .subscribe(object : ApiSubscriber<List<GankEntity>>(mView) {
                    override fun onNext(t: List<GankEntity>) {
                        mView?.onRefresh(t)
                    }
                })
    }

    override fun loadMore(pageSize: Int) {
        repository.getCollections(pageNum, pageSize)
                .compose(RxUtils.bindToLifecycle(mView))
                .compose(RxUtils.ioToMain())
                .subscribe(object : ApiSubscriber<List<GankEntity>>(mView) {
                    override fun onNext(data: List<GankEntity>?) {
                        pageNum++
                        if (data == null || data.isEmpty())
                            mView?.onNoMore()
                        else
                            mView?.onLoadMore(data)
                    }
                })
    }

}