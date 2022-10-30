package com.pakollya.exchangerates.names.presentation.adapter

import com.pakollya.exchangerates.base.presentation.adapter.BaseAdapter
import com.pakollya.exchangerates.base.presentation.adapter.ViewHolderFactory

interface CurrencyNameAdapter {
    class Names : BaseAdapter.Base(
        CurrencyNameViewHolderFactory(
            ViewHolderFactory.Exception()
        )
    )
}