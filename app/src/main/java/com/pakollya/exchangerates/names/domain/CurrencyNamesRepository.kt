package com.pakollya.exchangerates.names.domain

interface CurrencyNamesRepository {

    suspend fun names(): CurrencyNameDomain
}