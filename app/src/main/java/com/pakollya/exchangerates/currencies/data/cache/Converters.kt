package com.pakollya.exchangerates.currencies.data.cache

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrencyListConverter{
    private val gson = Gson()

    @TypeConverter
    fun stringToCurrencyList(data: String): List<Currency> {
        val currencyListType = object: TypeToken<List<Currency>>() {}.type
        return gson.fromJson(data, currencyListType)
    }

    @TypeConverter
    fun currencyListToString(currencyList: List<Currency>): String {
        return gson.toJson(currencyList)
    }
}

class SymbolsConverter{
    private val gson = Gson()

    @TypeConverter
    fun stringToSymbols(data: String): Map<String, String> {
        val symbolsType = object: TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(data, symbolsType)
    }

    @TypeConverter
    fun symbolsToString(symbols: Map<String, String>): String {
        return gson.toJson(symbols)
    }
}