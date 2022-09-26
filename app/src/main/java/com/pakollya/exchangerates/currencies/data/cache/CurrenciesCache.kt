package com.pakollya.exchangerates.currencies.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.pakollya.exchangerates.currencies.domain.CurrenciesDomain
import com.pakollya.exchangerates.sorting.domain.Sorting
import com.pakollya.exchangerates.sorting.domain.Sorting.*
import com.pakollya.exchangerates.utils.CURRENCIES
import java.text.SimpleDateFormat
import java.util.*

interface CurrenciesCache {

    fun <T> map(mapper: Mapper<T>): T

    @Entity(tableName = CURRENCIES)
    data class Currencies(
        @PrimaryKey
        val base: String,
        val date: String,
        @TypeConverters(CurrencyListConverter::class)
        val rates: List<Currency>
    ) : CurrenciesCache {

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(base, date, rates)

        fun sort(sorting: Sorting?): Currencies {
            val sortedList =
                when (sorting) {
                    BY_NAME -> rates.sortedBy { it.name }
                    BY_NAME_DESC -> rates.sortedByDescending { it.name }
                    BY_VALUE -> rates.sortedBy { it.value }
                    BY_VALUE_DESC -> rates.sortedByDescending { it.value }
                    else -> rates.sortedBy { it.name }
                }

            return Currencies(base, date, sortedList)
        }

        fun isValid(): Boolean {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return dateFormat.format(Date()).toString() == date
        }
    }

    interface Mapper<T> {

        fun map(base: String, date: String, currencies: List<Currency>): T

        class Base : Mapper<CurrenciesDomain> {

            override fun map(
                base: String,
                date: String,
                currencies: List<Currency>
            ): CurrenciesDomain = CurrenciesDomain.Base(base, date, currencies)
        }
    }
}