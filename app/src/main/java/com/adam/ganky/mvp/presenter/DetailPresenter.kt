package com.adam.ganky.mvp.presenter

import com.adam.ganky.base.BasePresenter
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.http.ErrorHandler
import com.adam.ganky.mvp.IDetail
import com.adam.ganky.mvp.repository.DetailRepository
import com.adam.ganky.rx.RxUtils
import javax.inject.Inject

/**
 * Created by yu on 2017/6/22.
 */
class DetailPresenter
@Inject constructor(var view: IDetail.View, var repository: DetailRepository)
    : BasePresenter<IDetail.View>(view), IDetail.Presenter {

    override fun getGirl() {
        repository.getGirl()
                .compose(RxUtils.defaultTransformer(mView))
                .subscribe({ mView?.showGirl(it.get(0).url!!) }, { ErrorHandler.handleException(it) })
                .apply { addDisposable(this) }
    }

    override fun isFavorite(id: String) {
        mView?.onFavoriteChange(repository.queryById(id) != null)
    }

    override fun addToFavorites(entity: GankEntity) {
        repository.addToFavorites(entity)
        mView?.onFavoriteChange(true)
    }

    override fun removeById(id: String) {
        repository.removeById(id)
        mView?.onFavoriteChange(false)
    }
}