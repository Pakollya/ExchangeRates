package com.pakollya.exchangerates.currencies.data.cloud

import com.pakollya.exchangerates.currencies.data.cache.Currency
import com.pakollya.exchangerates.currencies.data.cache.CurrencyCache.Currencies
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExchangeRatesMapperTest {

    private lateinit var mapper: ExchangeRatesCloud.Mapper<Currencies>

    @Before
    fun init() {
        mapper = ExchangeRatesCloud.Mapper.Base()
    }

    @Test
    fun `test map`() {
        val actual = mapper.map("EUR", "2020",
            mapOf(
                Pair("BTC", 0.1234),
                Pair("USD", 0.4321)
            ))
        val expected = Currencies("EUR", "2020",
            listOf(
                Currency("BTC", 0.1234),
                Currency("USD", 0.4321)
            ))

        assertEquals(expected, actual)
    }
}