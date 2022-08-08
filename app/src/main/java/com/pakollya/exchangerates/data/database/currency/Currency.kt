package com.pakollya.exchangerates.data.database.currency

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pakollya.exchangerates.utils.CURRENCY_LIST

@Entity(tableName = CURRENCY_LIST)
data class Currency(
    @PrimaryKey
    val name: String,
    val value: Double?
): Item