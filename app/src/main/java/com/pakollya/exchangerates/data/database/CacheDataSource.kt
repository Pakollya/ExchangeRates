package com.pakollya.exchangerates.data.database

import android.content.Context
import androidx.room.Room

interface CacheDataSource:
    CurrencyRateDataSource, CurrencyListDataSource,
    CurrencyNameDataSource, CurrencyFavoriteDataSource, CurrencySettingsDataSource
{

    class BaseDataSource(context: Context, databaseName: String) : CacheDataSource {

        private val appDatabase: AppDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            databaseName
        ).build()

        override fun currencyRateDao(): CurrencyRateDao {
            return appDatabase.currencyRateDao()
        }

        override fun currencyListDao(): CurrencyListDao {
            return  appDatabase.currencyListDao()
        }

        override fun currencyNameDao(): CurrencyNameDao {
            return appDatabase.currencyNameDao()
        }

        override fun currencyFavoriteDao(): CurrencyFavoriteDao {
            return appDatabase.currencyFavoriteDao()
        }

        override fun currencySettingsDao(): CurrencySettingsDao {
            return appDatabase.currencySettingsDao()
        }
    }
}

interface CurrencyRateDataSource{
    fun currencyRateDao(): CurrencyRateDao
}

interface CurrencyListDataSource{
    fun currencyListDao(): CurrencyListDao
}

interface CurrencyNameDataSource{
    fun currencyNameDao(): CurrencyNameDao
}

interface CurrencyFavoriteDataSource{
    fun currencyFavoriteDao(): CurrencyFavoriteDao
}

interface CurrencySettingsDataSource{
    fun currencySettingsDao(): CurrencySettingsDao
}