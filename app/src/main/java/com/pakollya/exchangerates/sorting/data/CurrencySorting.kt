package com.pakollya.exchangerates.sorting.data

import com.pakollya.exchangerates.base.data.PreferenceDataStore

interface CurrencySorting {

    interface Save : com.pakollya.exchangerates.base.core.Save<String>
    interface Read : com.pakollya.exchangerates.base.core.Read<String>

    interface Mutable : Save, Read

    class Base(private val preferences: PreferenceDataStore<String>) : Mutable {

        override fun save(data: String) = preferences.save(CURRENCY_SORTING_KEY, data)

        override fun read() = preferences.read(CURRENCY_SORTING_KEY)

        companion object {
            private const val CURRENCY_SORTING_KEY = "CurrencySortingKey"
        }
    }
}