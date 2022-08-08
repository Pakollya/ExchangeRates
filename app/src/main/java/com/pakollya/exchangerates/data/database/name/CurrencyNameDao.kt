package com.pakollya.exchangerates.data.database.name

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyNameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencyNameList(nameList: List<CurrencyName>?)

    @Query("SELECT * FROM currency_name")
    fun currencyNameList(): Flow<List<CurrencyName>?>

    @Query("SELECT fullName FROM currency_name WHERE name = :name")
    fun currencyFullNameByName(name: String): String?

    @Query("SELECT EXISTS(SELECT * FROM currency_name WHERE name = :name)")
    fun hasCurrencyName(name: String): Boolean
}