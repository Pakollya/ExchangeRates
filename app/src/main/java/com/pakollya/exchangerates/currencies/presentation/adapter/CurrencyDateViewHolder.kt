package com.pakollya.exchangerates.currencies.presentation.adapter

import com.pakollya.exchangerates.base.presentation.ItemUi
import com.pakollya.exchangerates.base.presentation.adapter.BaseViewHolder
import com.pakollya.exchangerates.databinding.CurrencyDateLayoutBinding

class CurrencyDateViewHolder(
    private val binding: CurrencyDateLayoutBinding
) : BaseViewHolder<ItemUi>(binding) {

    override fun bind(item: ItemUi) = item.show(binding.date)
}