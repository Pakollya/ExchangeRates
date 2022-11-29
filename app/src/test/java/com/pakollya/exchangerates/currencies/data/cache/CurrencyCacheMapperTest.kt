package com.pakollya.exchangerates.currencies.data.cache

import com.pakollya.exchangerates.currencies.domain.CurrenciesDomain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrencyCacheMapperTest {

    private lateinit var mapper: CurrencyCache.Mapper<CurrenciesDomain>

    @Before
    fun init() {
        mapper = CurrencyCache.Mapper.Base()
    }

    @Test
    fun `test map`() {
        val actual = mapper.map("EUR", "2020",
            listOf(
                Currency("BTC", 0.1234),
                Currency("USD", 0.4321)
            ))
        val expected = CurrenciesDomain.Base("EUR", "2020",
            listOf(
                Currency("BTC", 0.1234),
                Currency("USD", 0.4321)
            ))

        assertEquals(expected, actual)
    }
}