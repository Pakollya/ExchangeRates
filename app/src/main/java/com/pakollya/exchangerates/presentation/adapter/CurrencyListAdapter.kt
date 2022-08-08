package com.pakollya.exchangerates.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.pakollya.exchangerates.R
import com.pakollya.exchangerates.data.database.currency.Currency
import com.pakollya.exchangerates.databinding.ListItemCurrencyBinding
import com.pakollya.exchangerates.domain.CurrencyInteractor

class CurrencyListAdapter(
    private val interactor: CurrencyInteractor,
    private val favoriteClick: (String) -> Unit
) : ListAdapter<Currency, CurrencyViewHolder>(CurrencyDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CurrencyViewHolder(
        ListItemCurrencyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        interactor,
        favoriteClick
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