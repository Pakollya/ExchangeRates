package com.pakollya.exchangerates.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pakollya.exchangerates.data.database.entity.Currency

class CurrencyListConverter{
    private val gson = Gson()

    @TypeConverter
    fun stringToCurrency(data: String?): List<Currency>? {
        return if (data.isNullOrEmpty()) {
            null
        } else {
            val currencyType = object: TypeToken<List<Currency>?>() {}.type
            gson.fromJson(data, currencyType)
        }
    }

    @TypeConverter
    fun currencyToString(currencyList: List<Currency>?): String {
        return if (currencyList == null) "" else gson.toJson(currencyList)
    }
}