package com.pakollya.exchangerates.data.database

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.pakollya.exchangerates.data.database.entity.*

@Dao
interface CurrencyRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencyRate(currencyRate: CurrencyRate)

    @Query("SELECT * FROM currency_rate WHERE name = :name")
    fun currencyRateByName(name: String): CurrencyRate?

    @Query("SELECT EXISTS(SELECT * FROM currency_rate WHERE name = :name)")
    fun hasCurrencyRate(name: String): Boolean
}

@Dao
interface CurrencyListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencyList(currencyList: List<Currency>?)

    @RawQuery
    fun currencyList(query: SupportSQLiteQuery): List<Currency>?
}

@Dao
interface CurrencyNameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencyNameList(nameList: List<CurrencyName>?)

    @Query("SELECT * FROM currency_name")
    fun currencyNameList(): List<CurrencyName>?

    @Query("SELECT fullName FROM currency_name WHERE name = :name")
    fun currencyFullNameByName(name: String): String?

    @Query("SELECT EXISTS(SELECT * FROM currency_name WHERE name = :name)")
    fun hasCurrencyName(name: String): Boolean
}

@Dao
interface CurrencyFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencyFavorite(favorite: CurrencyFavorite)

    @Query("DELETE FROM currency_favorite WHERE name = :name")
    fun deleteCurrencyFavoriteByName(name: String)
}

@Dao
interface CurrencySettingsDao {
    @Query("SELECT currencySortedByType FROM settings WHERE id = 0")
    fun getSortedByType(): Int?

    @Query("UPDATE settings SET currencySortedByType =:sortedByType WHERE id = 0")
    fun updateSortedByType(sortedByType: Int?)

    @Query("SELECT EXISTS(SELECT * FROM settings WHERE id = 0)")
    fun hasCurrencySettings(): Boolean

    @Query("INSERT INTO settings (id, currencySortedByType) VALUES (0, null)")
    fun setDefaultSettings()
}