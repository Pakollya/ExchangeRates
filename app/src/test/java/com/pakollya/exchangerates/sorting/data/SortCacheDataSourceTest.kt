package com.pakollya.exchangerates.sorting.data

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SortCacheDataSourceTest {

    private lateinit var dataSource: SortCacheDataSource
    private lateinit var currencySorting: TestCurrencySorting

    @Before
    fun init() {
        currencySorting = TestCurrencySorting()
        dataSource = SortCacheDataSource.Base(currencySorting)
    }

    @Test
    fun `test read sorting empty`() {
        val actual = dataSource.read()
        val expected = "by name"

        assertEquals(expected, actual)
    }

    @Test
    fun `test read sorting not empty`() {
        currencySorting.sorting = "by value"
        val actual = dataSource.read()
        val expected = "by value"

        assertEquals(expected, actual)
    }

    @Test
    fun `test change sorting`() {
        dataSource.changeSort("by value")

        val actual = currencySorting.sorting
        val expected = "by value"

        assertEquals(expected, actual)
    }

    @Test
    fun `test is sorting`() {
        val actual = dataSource.isSorting("by name")
        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    fun `test is not sorting`() {
        currencySorting.sorting = "by value"
        val actual = dataSource.isSorting("by name")
        val expected = false

        assertEquals(expected, actual)
    }

    private class TestCurrencySorting : CurrencySorting.Mutable {
        var sorting = ""

        override fun read() = sorting

        override fun save(data: String) {
            sorting = data
        }
    }
}