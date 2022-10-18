package com.pakollya.exchangerates.favorites.domain

import com.pakollya.exchangerates.currencies.domain.CurrenciesDomain

interface FavoriteRepository {

    suspend fun favorites(): CurrenciesDomain
}