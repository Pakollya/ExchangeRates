package com.pakollya.exchangerates.favorites.data

import com.pakollya.exchangerates.currencies.data.cache.Currency
import com.pakollya.exchangerates.currencies.data.cache.CurrencyCache
import com.pakollya.exchangerates.currencies.data.cache.CurrencyCache.Mapper
import com.pakollya.exchangerates.currencies.domain.CurrenciesDomain

interface FavoriteMapper : Mapper<CurrenciesDomain> {

    class Base(
        private val isFavorite: IsFavorite
    ) : FavoriteMapper {

        override fun map(
            base: String,
            date: String,
            currencies: List<Currency>
        ): CurrenciesDomain {
            val itemList = currencies
                .filter { isFavorite.isFavorite(it.name) }

            return CurrenciesDomain.Base(base, date, itemList)
        }
    }
}