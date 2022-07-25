package com.pakollya.exchangerates.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pakollya.exchangerates.data.database.entity.*

@Database(
    entities = [
        CurrencyRate::class,
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

    abstract fun currencyRateDao(): CurrencyRateDao
    abstract fun currencyListDao(): CurrencyListDao
    abstract fun currencyNameDao(): CurrencyNameDao
    abstract fun currencyFavoriteDao(): CurrencyFavoriteDao
    abstract fun currencySettingsDao(): CurrencySettingsDao
}