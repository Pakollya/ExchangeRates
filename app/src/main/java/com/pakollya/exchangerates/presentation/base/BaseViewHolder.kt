package com.pakollya.exchangerates.presentation.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.pakollya.exchangerates.data.database.entity.Item

abstract class BaseViewHolder<out V : ViewBinding, I : Item>(
    private val binding: V
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var item: I

    open fun onBind(item: I) {
        this.item = item
    }
}