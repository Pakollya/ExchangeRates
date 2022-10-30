package com.pakollya.exchangerates.sorting.domain

import com.pakollya.exchangerates.utils.SORT_BY_NAME
import com.pakollya.exchangerates.utils.SORT_BY_NAME_DESC
import com.pakollya.exchangerates.utils.SORT_BY_VALUE
import com.pakollya.exchangerates.utils.SORT_BY_VALUE_DESC

enum class Sorting(val sorting: String) {
    BY_NAME(SORT_BY_NAME),
    BY_NAME_DESC(SORT_BY_NAME_DESC),
    BY_VALUE(SORT_BY_VALUE),
    BY_VALUE_DESC(SORT_BY_VALUE_DESC);

    companion object {
        fun sorting(sortName: String) = values().firstOrNull { it.sorting == sortName }
    }
}
