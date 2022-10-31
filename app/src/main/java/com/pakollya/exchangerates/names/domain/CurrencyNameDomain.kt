package com.pakollya.exchangerates.names.domain

import com.pakollya.exchangerates.base.presentation.ItemUi
import com.pakollya.exchangerates.currencies.data.BaseCurrencyCacheDataSource
import com.pakollya.exchangerates.names.presentation.ChangeBaseCurrency
import com.pakollya.exchangerates.names.presentation.CurrencyNameUi
import com.pakollya.exchangerates.names.presentation.ItemsUi

interface CurrencyNameDomain {

    fun <T> map(mapper: Mapper<T>): T

    class Base(
        private val list: List<Pair<String, String>>
    ) : CurrencyNameDomain {

        override fun <T> map(mapper: Mapper<T>) = mapper.map(list)
    }

    interface Mapper<T> {

        fun map(list: List<Pair<String, String>>): T

        class Base(
            private val cacheDataSource: BaseCurrencyCacheDataSource,
            private val changeBase: ChangeBaseCurrency
        ) : Mapper<ItemsUi> {

            override fun map(list: List<Pair<String, String>>): ItemsUi {
                val itemList = mutableListOf<ItemUi>()
                itemList.addAll(list.map {
                    CurrencyNameUi(
                        id = it.first, name = it.first, fullName = it.second,
                        isBase = cacheDataSource.isBase(it.first), changeBase = changeBase
                    )
                })

                return ItemsUi.Base(itemList)
            }
        }
    }
}