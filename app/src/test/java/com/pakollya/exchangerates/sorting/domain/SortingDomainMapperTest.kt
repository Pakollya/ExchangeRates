package com.pakollya.exchangerates.sorting.domain

import com.pakollya.exchangerates.currencies.presentation.BaseTest
import com.pakollya.exchangerates.names.presentation.ItemsUi
import com.pakollya.exchangerates.sorting.presentation.SortingUi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SortingDomainMapperTest : BaseTest() {

    private lateinit var mapper: SortingDomain.Mapper<ItemsUi>
    private lateinit var dataSource: TestSortCacheDataSource
    private lateinit var changeSort: TestChangeSort

    @Before
    fun init() {
        dataSource = TestSortCacheDataSource()
        changeSort = TestChangeSort()
        mapper = SortingDomain.Mapper.Base(dataSource, changeSort)
    }

    @Test
    fun `test map`() {
        val actual = mapper.map(listOf("by name", "by value"))
        val expected = ItemsUi.Base(listOf(
            SortingUi("by name", "by name", false, changeSort),
            SortingUi("by value", "by value", false, changeSort)
        ))

        assertEquals(expected, actual)
    }
}