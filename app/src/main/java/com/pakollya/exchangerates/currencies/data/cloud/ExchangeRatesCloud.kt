package com.pakollya.exchangerates.currencies.data.cloud

import com.google.gson.annotations.SerializedName
import com.pakollya.exchangerates.currencies.data.cache.Currency
import com.pakollya.exchangerates.currencies.data.cache.CurrencyCache.*

interface ExchangeRatesCloud {

    fun <T> map(mapper: Mapper<T>): T

    class Empty : ExchangeRatesCloud {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(
            "none", "none",
            emptyMap()
        )
    }

    data class ExchangeRates(
        @SerializedName("base")
        private val base: String,
        @SerializedName("date")
        private val date: String,
        @SerializedName("rates")
        private val rates: Map<String, Double>
    ) : ExchangeRatesCloud {

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(base, date, rates)
    }

    interface Mapper<T> {

        fun map(base: String, date: String, currencies: Map<String, Double>): T

        class Base : Mapper<Currencies> {

            override fun map(
                base: String,
                date: String,
                currencies: Map<String, Double>
            ): Currencies = Currencies(
                base,
                date,
                currencies
                    .map { Currency(it.key, it.value) }
                    .sortedBy { it.name }
                    .toList())

        }
    }
}