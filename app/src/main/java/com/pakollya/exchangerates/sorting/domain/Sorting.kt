package com.pakollya.exchangerates.sorting.domain

enum class Sorting(val sorting: String) {
    BY_NAME("by name"),
    BY_NAME_DESC("by name desc"),
    BY_VALUE("by value"),
    BY_VALUE_DESC("by value desc");

    companion object {
        fun sorting(sortName: String) = values().firstOrNull { it.sorting == sortName }
    }
}
