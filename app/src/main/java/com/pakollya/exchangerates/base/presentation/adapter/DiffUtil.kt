package com.pakollya.exchangerates.base.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.pakollya.exchangerates.base.presentation.ItemUi

class DiffUtil<T : ItemUi>(
    private val oldList: List<T>,
    private val newList: List<T>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id() == newList[newItemPosition].id()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].content() == newList[newItemPosition].content()
}