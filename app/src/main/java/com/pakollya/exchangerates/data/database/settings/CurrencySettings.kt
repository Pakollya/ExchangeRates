package com.pakollya.exchangerates.data.database.settings

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pakollya.exchangerates.presentation.list.SortedByType
import com.pakollya.exchangerates.utils.SETTINGS

@Entity(tableName = SETTINGS)
data class CurrencySettings(
    @PrimaryKey
    val id: Int,
    val currencySortedByType: SortedByType? = null,
    val baseName: String? = null
)