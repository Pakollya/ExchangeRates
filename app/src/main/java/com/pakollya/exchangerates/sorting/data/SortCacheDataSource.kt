package com.pakollya.exchangerates.sorting.data

import com.pakollya.exchangerates.base.core.Read
import com.pakollya.exchangerates.sorting.presentation.ChangeSort

interface SortCacheDataSource : ChangeSort, IsSorting, Read<String> {

    class Base(
        private val currencySorting: CurrencySorting.Mutable,
    ) : SortCacheDataSource {
        private var cached = read()

        override fun changeSort(sorting: String) {
            currencySorting.save(sorting)
            cached = read()
        }

        override fun isSorting(sorting: String) = cached == sorting

        override fun read() = currencySorting
            .read()
            .ifEmpty {
                changeSort(SORT_BY_NAME)
                SORT_BY_NAME
            }

        companion object {
            private const val SORT_BY_NAME = "by name"
        }
    }
}