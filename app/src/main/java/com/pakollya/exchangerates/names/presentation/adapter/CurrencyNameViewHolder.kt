package com.pakollya.exchangerates.names.presentation.adapter

import com.pakollya.exchangerates.base.presentation.ItemUi
import com.pakollya.exchangerates.base.presentation.adapter.BaseViewHolder
import com.pakollya.exchangerates.databinding.CurrencyNameLayoutBinding

class CurrencyNameViewHolder(
    private val binding: CurrencyNameLayoutBinding
) : BaseViewHolder<ItemUi>(binding) {

    override fun bind(item: ItemUi) =
        item.show(
            binding.name,
            binding.fullName,
            binding.checkImage,
            binding.nameItem
        )
}