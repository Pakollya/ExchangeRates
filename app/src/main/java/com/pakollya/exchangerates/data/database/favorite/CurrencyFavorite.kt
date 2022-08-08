package com.pakollya.exchangerates.data.database.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pakollya.exchangerates.utils.CURRENCY_FAVORITE
import com.pakollya.exchangerates.utils.NAME

@Entity(tableName = CURRENCY_FAVORITE)
data class CurrencyFavorite(
    @PrimaryKey
    @ColumnInfo(name = NAME)
    val name: String
)