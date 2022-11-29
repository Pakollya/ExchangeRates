package com.pakollya.exchangerates.names.data.cloud

import com.pakollya.exchangerates.currencies.presentation.BaseTest
import com.pakollya.exchangerates.names.data.cloud.CurrencyNameCloud.CurrencyNames
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrencyNamesCloudDataSourceTest : BaseTest() {

    private lateinit var dataSource: CurrencyNamesCloudDataSource
    private lateinit var service: CurrencyNamesService

    @Before
    fun init() {
        service = TestCurrencyNamesService()
        dataSource = CurrencyNamesCloudDataSource.Base(service, TestHandleError())
    }

    @Test
    fun `test names`() = runBlocking {
        val actual = dataSource.names()
        val expected = CurrencyNames(
            mapOf(
                Pair("BTC", "Bitcoin"),
                Pair("EUR", "Euro")
            ))

        assertEquals(expected, actual)
    }

    private class TestCurrencyNamesService : CurrencyNamesService {

        override suspend fun names(apiKey: String) = CurrencyNames(
            mapOf(
                Pair("BTC", "Bitcoin"),
                Pair("EUR", "Euro")
            ))
    }
}