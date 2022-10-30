package com.pakollya.exchangerates.sorting.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.pakollya.exchangerates.base.presentation.ItemUi
import com.pakollya.exchangerates.base.presentation.adapter.BaseViewHolder
import com.pakollya.exchangerates.base.presentation.adapter.ViewHolderFactory
import com.pakollya.exchangerates.databinding.SortingLayoutBinding

class SortingViewHolderFactory(
    private val viewHolderFactory: ViewHolderFactory<ItemUi>
) : ViewHolderFactory<ItemUi> {

    override fun viewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemUi> =
        if (viewType == 5)
            SortingViewHolder(
                SortingLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else
            viewHolderFactory.viewHolder(parent, viewType)
}