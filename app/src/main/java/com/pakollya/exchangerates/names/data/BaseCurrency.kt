package com.pakollya.exchangerates.names.data

import com.pakollya.exchangerates.base.data.PreferenceDataStore

interface BaseCurrency {

    interface Save : com.pakollya.exchangerates.base.core.Save<String>
    interface Read : com.pakollya.exchangerates.base.core.Read<String>

    interface Mutable : Save, Read

    class Base(private val preferences: PreferenceDataStore<String>) : Mutable {

        override fun save(data: String) = preferences.save(BASE_CURRENCY_KEY, data)

        override fun read() = preferences.read(BASE_CURRENCY_KEY)

        companion object {
            private const val BASE_CURRENCY_KEY = "BaseCurrencyKey"
        }
    }
}