package com.pakollya.exchangerates.data.database.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencyFavorite(favorite: CurrencyFavorite)

    @Query("SELECT EXISTS(SELECT * FROM currency_favorite WHERE name = :name)")
    fun hasCurrencyFavoriteByName(name: String): Boolean

    @Query("DELETE FROM currency_favorite WHERE name = :name")
    fun deleteCurrencyFavoriteByName(name: String)
}