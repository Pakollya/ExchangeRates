package com.pakollya.exchangerates.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.pakollya.exchangerates.data.database.CurrencyListConverter

@Entity(tableName = "currency_rate")
data class CurrencyRate(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "currencyList")
    @TypeConverters(CurrencyListConverter::class)
    val currencyList: List<Currency>?
)

@Entity(tableName = "currency_list")
data class Currency(
    @PrimaryKey
//    @ColumnInfo(name = "name")
    val name: String,
//    @ColumnInfo(value = "value")
    val value: Double?
): Item

@Entity(tableName = "currency_name")
data class CurrencyName(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "fullName")
    val fullName: String
): Item

@Entity(tableName = "currency_favorite")
data class CurrencyFavorite(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String
)

@Entity(tableName = "settings")
data class CurrencySettings(
    @PrimaryKey
    val id: Int,
    val currencySortedByType: Int? = null
)