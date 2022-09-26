package com.pakollya.exchangerates.currencies.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pakollya.exchangerates.currencies.data.cache.CurrenciesCache.Currencies
import com.pakollya.exchangerates.names.data.cache.CurrencyNameDao
import com.pakollya.exchangerates.names.data.cloud.CurrencyNameCloud.CurrencyNames

@Database(
    entities = [
        Currencies::class,
        CurrencyNames::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(
    value = [
        CurrencyListConverter::class,
        SymbolsConverter::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrenciesDao
    abstract fun currencyNameDao(): CurrencyNameDao
}