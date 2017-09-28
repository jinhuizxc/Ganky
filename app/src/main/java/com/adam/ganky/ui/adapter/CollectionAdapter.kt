package com.adam.ganky.ui.adapter

import android.content.Context
import android.view.View
import com.adam.ganky.R
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.util.CategoryType
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author yu
 * Create on 2017/6/22.
 */
class CollectionAdapter(val context: Context, layoutId: Int, data: List<GankEntity>?,
                        listener: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit)
    : BaseQuickAdapter<GankEntity, BaseViewHolder>(layoutId, data) {

    init {
        setOnItemClickListener { adapter, view, position -> listener(adapter, view, position) }
    }

    override fun convert(helper: BaseViewHolder, item: GankEntity) {
        helper.setText(R.id.tvDesc, item.desc)
                .setImageResource(R.id.ivImage,
                        if (item.type == CategoryType.ANDROID.nameStr) R.mipmap.icon_android
                        else R.mipmap.icon_apple)
    }
}