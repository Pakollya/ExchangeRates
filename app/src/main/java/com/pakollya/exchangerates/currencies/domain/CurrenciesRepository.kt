package com.pakollya.exchangerates.currencies.domain

interface CurrenciesRepository {

    suspend fun currencies(): CurrenciesDomain

    fun baseCurrency(): String
}

