package com.pakollya.exchangerates.currencies.data.cache

import android.content.Context
import androidx.room.Room
import com.pakollya.exchangerates.names.data.cache.CurrencyNameDao

interface CurrenciesCacheDataSource : CurrencyDataSource, CurrencyNameDataSource {
    class Base(context: Context, databaseName: String) : CurrenciesCacheDataSource {

        private val appDatabase: AppDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            databaseName
        ).build()

        override fun currencyDao(): CurrenciesDao {
            return appDatabase.currencyDao()
        }

        override fun currencyNameDao(): CurrencyNameDao {
            return appDatabase.currencyNameDao()
        }
    }
}

interface CurrencyDataSource {
    fun currencyDao(): CurrenciesDao
}

interface CurrencyNameDataSource {
    fun currencyNameDao(): CurrencyNameDao
}