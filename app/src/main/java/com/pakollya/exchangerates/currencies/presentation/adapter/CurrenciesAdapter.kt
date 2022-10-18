package com.pakollya.exchangerates.currencies.presentation.adapter

import com.pakollya.exchangerates.base.presentation.adapter.BaseAdapter
import com.pakollya.exchangerates.base.presentation.adapter.ViewHolderFactory

interface CurrenciesAdapter {

    class Currencies : BaseAdapter.Base(
        CurrencyViewHolderFactory(
            CurrencyDateViewHolderFactory(
                ViewHolderFactory.Exception()
            )
        )
    )
}