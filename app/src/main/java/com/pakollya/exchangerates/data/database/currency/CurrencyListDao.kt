package com.pakollya.exchangerates.data.database.currency

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface CurrencyListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencyList(currencyList: List<Currency>?)

    @RawQuery(observedEntities = [Currency::class])
    fun currencyList(query: SupportSQLiteQuery): List<Currency>?
}