package com.pakollya.exchangerates.currencies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.pakollya.exchangerates.base.presentation.ItemUi
import com.pakollya.exchangerates.base.presentation.adapter.BaseViewHolder
import com.pakollya.exchangerates.base.presentation.adapter.ViewHolderFactory
import com.pakollya.exchangerates.databinding.CurrencyDateLayoutBinding

class CurrencyDateViewHolderFactory(
    private val viewHolderFactory: ViewHolderFactory<ItemUi>
) : ViewHolderFactory<ItemUi> {

    override fun viewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemUi> =
        if (viewType == 2)
            CurrencyDateViewHolder(
                CurrencyDateLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    else
        viewHolderFactory.viewHolder(parent, viewType)
}