package com.pakollya.exchangerates.base.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pakollya.exchangerates.base.core.Mapper
import com.pakollya.exchangerates.base.presentation.ItemUi

abstract class BaseAdapter<T : ItemUi>(
    private val viewHolderFactory: ViewHolderFactory<T>
) : RecyclerView.Adapter<BaseViewHolder<T>>(), Mapper.Unit<List<T>> {

    private val list: MutableList<T> = mutableListOf()

    override fun getItemViewType(position: Int): Int = list[position].type()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> =
        viewHolderFactory.viewHolder(parent, viewType)

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    override fun map(data: List<T>) {
        val diffCallback = DiffUtil(list, data)
        val result = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(data)
        result.dispatchUpdatesTo(this)
    }

    abstract class Base(viewHolderFactory: ViewHolderFactory<ItemUi>) :
            BaseAdapter<ItemUi>(viewHolderFactory)
}