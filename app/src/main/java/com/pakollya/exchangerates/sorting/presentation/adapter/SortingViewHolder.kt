package com.pakollya.exchangerates.sorting.presentation.adapter

import com.pakollya.exchangerates.base.presentation.ItemUi
import com.pakollya.exchangerates.base.presentation.adapter.BaseViewHolder
import com.pakollya.exchangerates.databinding.SortingLayoutBinding

class SortingViewHolder(
    private val binding: SortingLayoutBinding
) : BaseViewHolder<ItemUi>(binding) {

    override fun bind(item: ItemUi) {
        item.show(
            binding.sortingText,
            binding.sortingImage,
            binding.sortingItem
        )
    }
}