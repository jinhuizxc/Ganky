package com.adam.ganky.mvp.presenter

import com.adam.ganky.base.BasePresenter
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.http.ApiSubscriber
import com.adam.ganky.mvp.IDetail
import com.adam.ganky.mvp.repository.DetailRepository
import com.adam.ganky.rx.RxUtils
import javax.inject.Inject

/**
 * Created by yu on 2017/6/22.
 */
class DetailPresenter
@Inject constructor(view: IDetail.View, var repository: DetailRepository)
    : BasePresenter<IDetail.View>(view), IDetail.Presenter {

    override fun create() {

    }

    override fun getGirl() {
        repository.getGirl()
                .compose(RxUtils.apiTransformer(mView))
                .subscribe(object : ApiSubscriber<List<GankEntity>>(mView) {
                    override fun onNext(t: List<GankEntity>) {
                        mView?.showGirl(t.get(0).url!!)
                    }
                })
    }
}