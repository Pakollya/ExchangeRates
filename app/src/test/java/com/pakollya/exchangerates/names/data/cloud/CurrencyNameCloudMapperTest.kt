package com.pakollya.exchangerates.names.data.cloud

import com.pakollya.exchangerates.names.data.cloud.CurrencyNameCloud.Mapper
import com.pakollya.exchangerates.names.domain.CurrencyNameDomain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrencyNameCloudMapperTest {

    private lateinit var mapper: Mapper<CurrencyNameDomain>

    @Before
    fun init() {
        mapper = Mapper.Base()
    }

    @Test
    fun `test map`() {
        val actual = mapper.map(
            mapOf(
                Pair("EUR", "Euro"),
                Pair("BTC", "Bitcoin")
            ))
        val expected = CurrencyNameDomain.Base(
            listOf(
                Pair("BTC", "Bitcoin"),
                Pair("EUR", "Euro")
            ))

        assertEquals(expected, actual)
    }
}