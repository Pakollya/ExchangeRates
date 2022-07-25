package com.pakollya.exchangerates.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.pakollya.exchangerates.data.database.entity.Currency

class CurrencyDiffUtil : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem == newItem
    }
}