package com.pakollya.exchangerates.sorting.domain

import com.pakollya.exchangerates.base.presentation.ItemUi
import com.pakollya.exchangerates.sorting.data.SortCacheDataSource
import com.pakollya.exchangerates.sorting.presentation.ChangeSort
import com.pakollya.exchangerates.names.presentation.ItemsUi
import com.pakollya.exchangerates.sorting.presentation.SortingUi

interface SortingDomain{
    fun <T> map(mapper: Mapper<T>): T

    class Base(
        private val list: List<String>
    ) : SortingDomain {

        override fun <T> map(mapper: Mapper<T>) = mapper.map(list)
    }

    interface Mapper<T> {
        fun map(list: List<String>): T

        class Base(
            private val sortCacheDataSource: SortCacheDataSource,
            private val changeSort: ChangeSort
        ) : Mapper<ItemsUi> {

            override fun map(list: List<String>): ItemsUi {
                val itemList = mutableListOf<ItemUi>()
                itemList.addAll(
                    list.map { sorting ->
                        SortingUi(sorting, sorting, sortCacheDataSource.isSorting(sorting), changeSort)
                    }
                )
                return ItemsUi.Base(itemList)
            }
        }
    }
}