package com.pakollya.exchangerates.utils

import com.pakollya.exchangerates.data.database.currency.Currency
import com.pakollya.exchangerates.data.database.name.CurrencyName
import org.apache.commons.lang3.StringEscapeUtils

fun String.toCurrencyList(): List<Currency>? {
    return if (!this.isNullOrEmpty()) {
        val stringList: List<String> = this
            .substringAfter("\"rates\":")
            .replace("{", "")
            .replace("}", "")
            .replace("\"", "")
            .replace(" ", "")
            .split(",")
        val listCurrency: MutableList<Currency> = mutableListOf()
        stringList.forEach {
            listCurrency.add(
                Currency(
                    it.substringBefore(":").trim(),
                    it.substringAfter(":").trim().toDoubleOrNull()
                )
            )
        }
        return listCurrency
    } else {
        null
    }
}

fun String.toCurrencyNameList(): List<CurrencyName>? {
    return if (!this.isNullOrEmpty()) {
        val stringList: List<String> = this
            .substringAfter("\"symbols\":")
            .replace("{", "")
            .replace("}", "")
            .replace("\"", "")
            .split(",")
        val listCurrencyName: MutableList<CurrencyName> = mutableListOf()
        stringList.forEach {
            val currencyName = CurrencyName(
                it.substringBefore(":").trim(),
                StringEscapeUtils.unescapeJava(it.substringAfter(":").trim())
            )
            listCurrencyName.add(currencyName)
        }
        return listCurrencyName
    } else {
        null
    }
}