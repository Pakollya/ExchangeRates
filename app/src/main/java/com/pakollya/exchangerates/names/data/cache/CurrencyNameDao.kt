package com.pakollya.exchangerates.names.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pakollya.exchangerates.names.data.cloud.CurrencyNameCloud.CurrencyNames

@Dao
interface CurrencyNameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNames(names: CurrencyNames?)

    @Query("SELECT * FROM currency_name")
    fun names(): CurrencyNames

    @Query("SELECT COUNT(*) FROM currency_name")
    fun namesCount(): Int?
}