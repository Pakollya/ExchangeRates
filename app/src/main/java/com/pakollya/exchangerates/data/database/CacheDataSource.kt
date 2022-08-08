package com.pakollya.exchangerates.data.database

import android.content.Context
import androidx.room.Room
import com.pakollya.exchangerates.data.database.currency.CurrencyListDao
import com.pakollya.exchangerates.data.database.favorite.CurrencyFavoriteDao
import com.pakollya.exchangerates.data.database.name.CurrencyNameDao
import com.pakollya.exchangerates.data.database.settings.CurrencySettingsDao

interface CacheDataSource: CurrencyListDataSource, CurrencyNameDataSource,
    CurrencyFavoriteDataSource, CurrencySettingsDataSource
{

    class BaseDataSource(context: Context, databaseName: String) : CacheDataSource {

        private val appDatabase: AppDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            databaseName
        ).build()

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