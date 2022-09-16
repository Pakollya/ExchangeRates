package com.pakollya.exchangerates.base.data

import android.content.SharedPreferences

interface PreferenceDataStore<T> {

    fun save(key: String, data: T)

    fun read(key: String): T

    class Favorites(private val sharedPreferences: SharedPreferences) : PreferenceDataStore<Set<String>> {

        override fun save(key: String, data: Set<String>) =
            sharedPreferences.edit().putStringSet(key, data).apply()

        override fun read(key: String): Set<String> =
            sharedPreferences.getStringSet(key, emptySet()) ?: emptySet()
    }

    class Base(private val sharedPreferences: SharedPreferences) : PreferenceDataStore<String> {
        override fun save(key: String, data: String) =
            sharedPreferences.edit().putString(key, data).apply()

        override fun read(key: String): String = sharedPreferences.getString(key, "") ?: ""
    }
}