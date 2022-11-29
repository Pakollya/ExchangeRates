package com.pakollya.exchangerates.favorites.data

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FavoritesCacheDataSourceTest {

    private lateinit var dataSource: FavoritesCacheDataSource
    private lateinit var favorites: TestFavoriteCurrencies

    @Before
    fun init() {
        favorites = TestFavoriteCurrencies()
        dataSource = FavoritesCacheDataSource.Base(favorites)
    }

    @Test
    fun `test change favorite`() {
        dataSource.changeFavorite("BTC")

        val actual = favorites.favorites.elementAt(0)
        val expected = "BTC"

        assertEquals(expected, actual)
    }

    @Test
    fun `test is favorite`() {
        favorites.favorites = mutableSetOf("BTC", "EUR", "USD")

        val actual = dataSource.isFavorite("EUR")
        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    fun `test is not favorite`() {
        favorites.favorites = mutableSetOf("BTC", "EUR")

        val actual = dataSource.isFavorite("USD")
        val expected = false

        assertEquals(expected, actual)
    }

    private class TestFavoriteCurrencies : FavoriteCurrencies.Mutable {

        var favorites = emptySet<String>()

        override fun read(): Set<String> = favorites

        override fun save(data: Set<String>) {
            favorites = data
        }
    }
}