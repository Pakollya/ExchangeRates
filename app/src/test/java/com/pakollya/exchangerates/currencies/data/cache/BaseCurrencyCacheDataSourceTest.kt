package com.pakollya.exchangerates.currencies.data.cache

import com.pakollya.exchangerates.names.data.BaseCurrency
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseCurrencyCacheDataSourceTest {

    private lateinit var dataSource: BaseCurrencyCacheDataSource
    private lateinit var baseCurrency: TestBaseCurrency

    @Before
    fun init() {
        baseCurrency = TestBaseCurrency()
        dataSource = BaseCurrencyCacheDataSource.Base(baseCurrency)
    }

    @Test
    fun `test read base currency empty`() {
        val actual = dataSource.read()
        val expected = ""

        assertEquals(expected, actual)
    }

    @Test
    fun `test read base currency not empty`() {
        baseCurrency.currency = "USD"
        val actual = dataSource.read()
        val expected = "USD"

        assertEquals(expected, actual)
    }

    @Test
    fun `test change base currency`() {
        dataSource.changeBase("EUR")

        val actual = baseCurrency.currency
        val expected = "EUR"

        assertEquals(expected, actual)
    }

    @Test
    fun `test is base currency`() {
        baseCurrency.currency = "USD"
        val actual = dataSource.isBase("USD")
        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    fun `test is not base currency`() {
        val actual = dataSource.isBase("BTC")
        val expected = false

        assertEquals(expected, actual)
    }

    private class TestBaseCurrency : BaseCurrency.Mutable {
        var currency = ""

        override fun read() = currency

        override fun save(data: String) {
            currency = data
        }
    }
}