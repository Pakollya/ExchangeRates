package com.pakollya.exchangerates.currencies.data.cache

import android.content.Context
import androidx.room.Room

interface CurrenciesCache {
    fun dataBase(): CurrenciesDatabase

    class Base(context: Context) : CurrenciesCache {

        private val database: CurrenciesDatabase by lazy {
            Room.databaseBuilder(
                context.applicationContext,
                CurrenciesDatabase::class.java,
                "currency"
            ).build()
        }

        override fun dataBase(): CurrenciesDatabase = database
    }
}