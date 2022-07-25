package com.pakollya.exchangerates.presentation.adapter

import com.pakollya.exchangerates.data.database.entity.Currency
import com.pakollya.exchangerates.databinding.ListItemCurrencyBinding
import com.pakollya.exchangerates.presentation.base.BaseViewHolder

class CurrencyViewHolder(
    private val binding: ListItemCurrencyBinding
//    ,
//    private val itemClick: (String) -> Unit
) : BaseViewHolder<ListItemCurrencyBinding, Currency>(binding) {

    override fun onBind(item: Currency) {
        binding.name.text = item.name
        binding.value.text = item.value.toString()
//        binding.currencyItem.setOnClickListener {
//            item.name?.let { itemClick(it) }
//        }
    }
}