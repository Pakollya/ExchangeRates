package com.pakollya.exchangerates.data.database.settings

import androidx.room.Dao
import androidx.room.Query
import com.pakollya.exchangerates.presentation.list.SortedByType
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencySettingsDao {
    @Query("SELECT currencySortedByType FROM settings WHERE id = 0")
    fun getSortedByType(): Flow<SortedByType?>

    @Query("UPDATE settings SET currencySortedByType =:sortedByType WHERE id = 0")
    fun updateSortedByType(sortedByType: SortedByType?)

    @Query("SELECT baseName FROM settings WHERE id = 0")
    fun getBaseName(): Flow<String?>

    @Query("UPDATE settings SET baseName =:baseName WHERE id = 0")
    fun updateBaseName(baseName: String?)

    @Query("SELECT EXISTS(SELECT * FROM settings WHERE id = 0)")
    fun hasCurrencySettings(): Boolean

    @Query("INSERT INTO settings (id, currencySortedByType, baseName) VALUES (0, null, null)")
    fun setDefaultSettings()
}