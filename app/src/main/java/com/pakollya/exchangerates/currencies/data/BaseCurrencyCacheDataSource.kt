package com.pakollya.exchangerates.currencies.data

import com.pakollya.exchangerates.base.core.Read
import com.pakollya.exchangerates.names.presentation.ChangeBaseCurrency
import com.pakollya.exchangerates.names.data.BaseCurrency
import com.pakollya.exchangerates.names.data.IsBase

interface BaseCurrencyCacheDataSource : ChangeBaseCurrency, IsBase, Read<String> {

    class Base(
        private val baseCurrency: BaseCurrency.Mutable
    ) : BaseCurrencyCacheDataSource {
        private var cached = read()

        override fun changeBase(base: String) {
            baseCurrency.save(base)
            cached = read()
        }

        override fun isBase(base: String) = cached == base

        override fun read() = baseCurrency.read()
    }
}