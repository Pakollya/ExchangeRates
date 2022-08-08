package com.pakollya.exchangerates.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.pakollya.exchangerates.R
import com.pakollya.exchangerates.data.database.name.CurrencyName
import com.pakollya.exchangerates.databinding.ListItemCurrencyNameBinding
import com.pakollya.exchangerates.domain.CurrencyInteractor

class CurrencyNameListAdapter(
    private val clickItem: (String) -> Unit,
    private val interactor: CurrencyInteractor
) : ListAdapter<CurrencyName, CurrencyNameViewHolder>(CurrencyNameDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CurrencyNameViewHolder(
        ListItemCurrencyNameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        interactor,
        clickItem
    )

    override fun onBindViewHolder(
        holder: CurrencyNameViewHolder,
        position: Int
    ) {
        holder.onBind(currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.list_item_currency_name
    }
}