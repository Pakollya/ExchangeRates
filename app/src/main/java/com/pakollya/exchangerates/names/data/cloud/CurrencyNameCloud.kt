package com.pakollya.exchangerates.names.data.cloud

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.pakollya.exchangerates.base.data.IsEmpty
import com.pakollya.exchangerates.names.domain.CurrencyNameDomain

interface CurrencyNameCloud {

    fun <T> map(mapper: Mapper<T>): T

    class Empty : CurrencyNameCloud {

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(emptyMap())
    }

    @Entity(tableName = "currency_name")
    data class CurrencyNames(
        @PrimaryKey
        @SerializedName("symbols")
        val symbols: Map<String, String>
    ) : CurrencyNameCloud, IsEmpty {

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(symbols)

        override fun isEmpty(): Boolean = symbols.isEmpty()
    }

    interface Mapper<T> {

        fun map(symbols: Map<String, String>): T

        class Base : Mapper<CurrencyNameDomain> {

            override fun map(
                symbols: Map<String, String>
            ): CurrencyNameDomain {
                val names = symbols.map { Pair(it.key, it.value) }.sortedBy { it.first }.toList()

                return CurrencyNameDomain.Base(names)
            }
        }
    }
}