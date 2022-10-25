package com.pakollya.exchangerates.favorites.data

import com.pakollya.exchangerates.base.data.PreferenceDataStore
import com.pakollya.exchangerates.utils.FAVORITE_KEY

interface FavoriteCurrencies {

    interface Save : com.pakollya.exchangerates.base.core.Save<Set<String>>
    interface Read : com.pakollya.exchangerates.base.core.Read<Set<String>>

    interface Mutable : Save, Read

    class Base(private val preferences: PreferenceDataStore<Set<String>>) : Mutable {

        override fun save(data: Set<String>) = preferences.save(FAVORITE_KEY, data)

        override fun read() = preferences.read(FAVORITE_KEY)
    }
}