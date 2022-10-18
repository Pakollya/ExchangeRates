package com.pakollya.exchangerates.currencies.presentation.adapter

import com.pakollya.exchangerates.base.presentation.ItemUi
import com.pakollya.exchangerates.base.presentation.adapter.BaseViewHolder
import com.pakollya.exchangerates.databinding.CurrencyLayoutBinding

class CurrencyViewHolder(
    private val binding: CurrencyLayoutBinding
) : BaseViewHolder<ItemUi>(binding) {

    override fun bind(item: ItemUi) {
        item.show(
            binding.name,
            binding.value,
            binding.imageFavorite,
            binding.favorite
        )
    }
}