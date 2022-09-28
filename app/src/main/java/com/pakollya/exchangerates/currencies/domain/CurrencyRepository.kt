package com.pakollya.exchangerates.currencies.domain

import com.pakollya.exchangerates.favorites.domain.FavoriteRepository

interface CurrencyRepository {

    suspend fun currencies(): CurrenciesDomain
}

interface CommonCurrencyRepository : CurrencyRepository, FavoriteRepository

