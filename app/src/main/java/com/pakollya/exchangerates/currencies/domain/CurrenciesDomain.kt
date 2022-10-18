package com.pakollya.exchangerates.currencies.domain

import com.pakollya.exchangerates.base.presentation.ItemUi
import com.pakollya.exchangerates.currencies.data.cache.Currency
import com.pakollya.exchangerates.favorites.data.FavoritesCacheDataSource
import com.pakollya.exchangerates.currencies.presentation.*
import com.pakollya.exchangerates.favorites.presentation.ChangeFavorite

interface CurrenciesDomain {

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        private val base: String,
        private val date: String,
        private val list: List<Currency>
    ) : CurrenciesDomain {

        override fun <T> map(mapper: Mapper<T>) = mapper.map(base, date, list)
    }

    interface Mapper<T> {

        fun map(base: String, date: String, list: List<Currency>): T

        class Base(
            private val cacheDataSource: FavoritesCacheDataSource,
            private val changeFavorite: ChangeFavorite
        ) : Mapper<CurrenсiesUi> {

            override fun map(
                base: String,
                date: String,
                list: List<Currency>
            ): CurrenсiesUi {
                val itemList = mutableListOf<ItemUi>(DateUi("Last update: $date"))
                itemList.addAll(list.map {
                    CurrencyUi(
                        id = it.name, name = it.name, value = "${it.value}",
                        isFavorite = cacheDataSource.isFavorite(it.name), changeFavorite = changeFavorite
                    )
                })
                val baseItem = BaseCurrencyUi(base)

                return CurrenсiesUi.Base(itemList, baseItem)
            }
        }
    }
}