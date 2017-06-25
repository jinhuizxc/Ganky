package com.adam.ganky.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import com.adam.ganky.R
import com.adam.ganky.entity.GankEntity
import com.adam.ganky.ui.widget.RoundImageView
import com.adam.ganky.util.CategoryType
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author yu
 * Create on 2017/6/22.
 */
class CategoryAdapter(val context: Context, data: List<GankEntity>?,
                      listener: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit)
    : BaseMultiItemQuickAdapter<GankEntity, BaseViewHolder>(data) {

    init {
        addItemType(CategoryType.ANDROID_IOS, R.layout.item_android)
        addItemType(CategoryType.GIRLS, R.layout.item_girls)
        setOnItemClickListener { adapter, view, position -> listener(adapter, view, position) }
    }

    override fun convert(helper: BaseViewHolder, item: GankEntity) {
        when (helper.itemViewType) {
            CategoryType.ANDROID_IOS -> {
                helper.setText(R.id.tvDesc, item.desc)
                        .setText(R.id.tvAuthor, if (TextUtils.isEmpty(item.who)) "UnKnow" else item.who)
                        .setText(R.id.tvDate, item.publishedAt)
                        .setImageResource(R.id.ivImage,
                                if (item.type == CategoryType.ANDROID_STR) R.mipmap.icon_android
                                else R.mipmap.icon_apple)
            }
            else -> {
                val iv: RoundImageView = helper.getView(R.id.ivImage)
                Glide.with(context).load(item.url).centerCrop().into(iv)
            }
        }
    }
}