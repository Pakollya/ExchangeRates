package com.pakollya.exchangerates.currencies.data.cache

import androidx.room.*
import com.pakollya.exchangerates.currencies.data.cache.CurrencyCache.Currencies

@Dao
interface CurrenciesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencies(currencies: Currencies)

    @Query("SELECT * FROM currencies WHERE base = :base")
    fun currencies(base: String): Currencies

    @Query("SELECT EXISTS(SELECT * FROM currencies WHERE base = :base)")
    fun currenciesExist(base: String): Boolean
}