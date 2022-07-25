package com.pakollya.exchangerates.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.pakollya.exchangerates.R
import com.pakollya.exchangerates.data.database.entity.Currency
import com.pakollya.exchangerates.databinding.ListItemCurrencyBinding

class CurrencyListAdapter(
//    private val clickItem: (String) -> Unit
) : ListAdapter<Currency, CurrencyViewHolder>(CurrencyDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CurrencyViewHolder(
        ListItemCurrencyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
//        ,
//        clickItem
    )

    override fun onBindViewHolder(
        holder: CurrencyViewHolder,
        position: Int
    ) {
        holder.onBind(currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.list_item_currency
    }
}