package com.pakollya.exchangerates.sorting.presentation.adapter

import com.pakollya.exchangerates.base.presentation.adapter.BaseAdapter
import com.pakollya.exchangerates.base.presentation.adapter.ViewHolderFactory

interface SortingAdapter {
    class Sorting : BaseAdapter.Base(
        SortingViewHolderFactory(
            ViewHolderFactory.Exception()
        )
    )
}