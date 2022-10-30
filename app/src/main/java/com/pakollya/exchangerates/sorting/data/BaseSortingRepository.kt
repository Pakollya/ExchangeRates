package com.pakollya.exchangerates.sorting.data

import com.pakollya.exchangerates.sorting.domain.SortingDomain
import com.pakollya.exchangerates.sorting.domain.SortingRepository
import com.pakollya.exchangerates.utils.SORT_BY_NAME
import com.pakollya.exchangerates.utils.SORT_BY_NAME_DESC
import com.pakollya.exchangerates.utils.SORT_BY_VALUE
import com.pakollya.exchangerates.utils.SORT_BY_VALUE_DESC

class BaseSortingRepository: SortingRepository {

    override fun sorting(): SortingDomain {
        val sorting = listOf(
            SORT_BY_NAME,
            SORT_BY_NAME_DESC,
            SORT_BY_VALUE,
            SORT_BY_VALUE_DESC
        )

        return SortingDomain.Base(sorting)
    }
}