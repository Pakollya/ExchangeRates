package com.pakollya.exchangerates.currencies.domain

import com.pakollya.exchangerates.currencies.data.cache.Currency
import com.pakollya.exchangerates.currencies.presentation.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrenciesDomainMapperTest : BaseTest() {

    private lateinit var mapper: CurrenciesDomain.Mapper<CurrenсiesUi>
    private lateinit var cacheDataSource: TestFavoritesCacheDataSource
    private lateinit var changeFavorite: TestChangeFavorite

    @Before
    fun init() {
        cacheDataSource = TestFavoritesCacheDataSource()
        changeFavorite = TestChangeFavorite()
        mapper = CurrenciesDomain.Mapper.Base(cacheDataSource, changeFavorite)
    }

    @Test
    fun `test map`() {
        val actual = mapper.map("EUR", "2020",
            listOf(
                Currency("BTC", 0.1234),
                Currency("USD", 0.4321)

            ))
        val expected = CurrenсiesUi.Base(
            mutableListOf(
                DateUi("Last update: 2020"),
                CurrencyUi("BTC", "BTC", "0.1234", true, changeFavorite),
                CurrencyUi("USD", "USD", "0.4321", true, changeFavorite)
            ),
            BaseCurrencyUi("EUR")
        )

        assertEquals(expected, actual)
    }
}