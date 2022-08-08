package com.pakollya.exchangerates.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.pakollya.exchangerates.data.database.name.CurrencyName

class CurrencyNameDiffUtil : DiffUtil.ItemCallback<CurrencyName>() {
    override fun areItemsTheSame(oldItem: CurrencyName, newItem: CurrencyName): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: CurrencyName, newItem: CurrencyName): Boolean {
        return oldItem == newItem
    }
}