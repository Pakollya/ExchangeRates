package com.pakollya.exchangerates.sorting.data

import com.pakollya.exchangerates.sorting.domain.SortingDomain
import com.pakollya.exchangerates.sorting.domain.SortingRepository

class BaseSortingRepository: SortingRepository {

    override fun sorting(): SortingDomain {
        val sorting = listOf(
            "by name",
            "by name desc",
            "by value",
            "by value desc"
        )

        return SortingDomain.Base(sorting)
    }
}