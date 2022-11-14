package com.pakollya.exchangerates.currencies.data.cloud

import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesCloud.ExchangeRates
import com.pakollya.exchangerates.currencies.presentation.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExchangeRatesCloudDataSourceTest : BaseTest() {

    private lateinit var dataSource: ExchangeRatesCloudDataSource
    private lateinit var service: ExchangeRatesService

    @Before
    fun init() {
        service = TestExchangeRatesService()
        dataSource = ExchangeRatesCloudDataSource.Base(service, TestHandleError())
    }

    @Test
    fun `test exchange rates`() = runBlocking {
        val actual = dataSource.exchangeRates("BTC")
        val expected = ExchangeRates("EUR", "2020",
            mapOf(
                Pair("BTC", 0.4444),
                Pair("USD", 0.1111)
            ))

        assertEquals(expected, actual)
    }

    @Test
    fun `test exchange rates with empty base`() = runBlocking {
        val actual = dataSource.exchangeRates("")
        val expected = ExchangeRates("BTC", "2022",
            mapOf(
                Pair("USD", 0.1234),
                Pair("EUR", 0.4321)
            ))

        assertEquals(expected, actual)
    }

    @Test
    fun `test exchange rates with base id null`() = runBlocking {
        val actual = dataSource.exchangeRates(null)
        val expected = ExchangeRates("BTC", "2022",
            mapOf(
                Pair("USD", 0.1234),
                Pair("EUR", 0.4321)
            ))

        assertEquals(expected, actual)
    }

    private class TestExchangeRatesService : ExchangeRatesService {

        override suspend fun exchangeRates(
            apiKey: String
        ) = ExchangeRates("BTC", "2022",
            mapOf(
                Pair("USD", 0.1234),
                Pair("EUR", 0.4321)
            ))

        override suspend fun exchangeRates(
            apiKey: String,
            base: String
        ) = ExchangeRates("EUR", "2020",
            mapOf(
                Pair("BTC", 0.4444),
                Pair("USD", 0.1111)
            ))
    }
}