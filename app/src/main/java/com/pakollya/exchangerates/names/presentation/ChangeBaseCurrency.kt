package com.pakollya.exchangerates.names.presentation

import com.pakollya.exchangerates.names.data.BaseCurrencyCommunication

interface ChangeBaseCurrency {

    fun changeBase(base: String)

    class Base(
        private val changeBase: ChangeBaseCurrency,
        private val communication: BaseCurrencyCommunication
    ) : ChangeBaseCurrency {

        override fun changeBase(base: String) {
            changeBase.changeBase(base)
            communication.map(base)
        }
    }
}