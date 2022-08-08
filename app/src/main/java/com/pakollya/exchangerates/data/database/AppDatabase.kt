package com.pakollya.exchangerates.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pakollya.exchangerates.data.database.currency.*
import com.pakollya.exchangerates.data.database.favorite.*
import com.pakollya.exchangerates.data.database.name.*
import com.pakollya.exchangerates.data.database.settings.*

@Database(
    entities = [
        Currency::class,
        CurrencyName::class,
        CurrencyFavorite::class,
        CurrencySettings::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [CurrencyListConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyListDao(): CurrencyListDao
    abstract fun currencyNameDao(): CurrencyNameDao
    abstract fun currencyFavoriteDao(): CurrencyFavoriteDao
    abstract fun currencySettingsDao(): CurrencySettingsDao
}