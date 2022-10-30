package com.pakollya.exchangerates.sorting.presentation

import com.pakollya.exchangerates.sorting.data.UpdateSorting

interface ChangeSort{

    fun changeSort(sorting: String)

    class Base(
        private val changeSort: ChangeSort,
        private val communication: UpdateSorting
    ) : ChangeSort {

        override fun changeSort(sorting: String) {
            changeSort.changeSort(sorting)
            communication.map(true)
        }
    }
}