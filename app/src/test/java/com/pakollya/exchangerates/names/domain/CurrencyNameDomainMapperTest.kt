package com.pakollya.exchangerates.names.domain

import com.pakollya.exchangerates.currencies.presentation.BaseTest
import com.pakollya.exchangerates.names.domain.CurrencyNameDomain.Mapper
import com.pakollya.exchangerates.names.presentation.CurrencyNameUi
import com.pakollya.exchangerates.names.presentation.ItemsUi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrencyNameDomainMapperTest : BaseTest() {

    private lateinit var mapper: Mapper<ItemsUi>
    private lateinit var dataSource: TestBaseCurrencyCacheDataSource
    private lateinit var changeBaseCurrency: TestChangeBaseCurrency

    @Before
    fun init() {
        dataSource = TestBaseCurrencyCacheDataSource()
        changeBaseCurrency = TestChangeBaseCurrency()
        mapper = Mapper.Base(dataSource, changeBaseCurrency)
    }

    @Test
    fun `test map`() {
        val actual = mapper.map(
            listOf(
                Pair("BTC", "Bitcoin"),
                Pair("EUR", "Euro")
            ))
        val expected = ItemsUi.Base(listOf(
            CurrencyNameUi("BTC", "BTC", "Bitcoin", true, changeBaseCurrency),
            CurrencyNameUi("EUR", "EUR", "Euro", true, changeBaseCurrency)
        ))

        assertEquals(expected, actual)
    }
}