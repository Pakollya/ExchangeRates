package com.pakollya.exchangerates.favorites.data

import com.pakollya.exchangerates.currencies.data.cache.Currency
import com.pakollya.exchangerates.currencies.domain.CurrenciesDomain
import com.pakollya.exchangerates.currencies.presentation.BaseTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FavoriteMapperTest : BaseTest() {

    private lateinit var mapper: FavoriteMapper
    private lateinit var dataSource: TestFavoritesCacheDataSource

    @Before
    fun init() {
        dataSource = TestFavoritesCacheDataSource()
        mapper = FavoriteMapper.Base(dataSource)
    }

    @Test
    fun `test map`() {
        val actual = mapper.map("BTC", "2022",
            listOf(
                Currency("EUR", 0.1234),
                Currency("USD", 0.4321)
            ))
        val expected = CurrenciesDomain.Base("BTC", "2022",
            listOf(
                Currency("EUR", 0.1234),
                Currency("USD", 0.4321)
            ))

        assertEquals(expected, actual)
    }
}