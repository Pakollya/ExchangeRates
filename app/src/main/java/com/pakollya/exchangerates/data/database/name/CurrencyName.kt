package com.pakollya.exchangerates.data.database.name

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pakollya.exchangerates.data.database.currency.Item
import com.pakollya.exchangerates.utils.CURRENCY_NAME
import com.pakollya.exchangerates.utils.FULL_NAME
import com.pakollya.exchangerates.utils.NAME

@Entity(tableName = CURRENCY_NAME)
data class CurrencyName(
    @PrimaryKey
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = FULL_NAME)
    val fullName: String?
): Item