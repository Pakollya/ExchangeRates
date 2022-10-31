package com.pakollya.exchangerates.base.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.pakollya.exchangerates.base.presentation.ItemUi

abstract class BaseViewHolder<T : ItemUi> (
    private val binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: T)
}