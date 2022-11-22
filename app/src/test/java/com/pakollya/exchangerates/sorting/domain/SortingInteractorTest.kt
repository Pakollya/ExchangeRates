package com.pakollya.exchangerates.sorting.domain

import com.pakollya.exchangerates.currencies.presentation.BaseTest
import com.pakollya.exchangerates.names.presentation.ItemsUi
import com.pakollya.exchangerates.sorting.presentation.SortingUi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SortingInteractorTest : BaseTest() {

    private lateinit var interactor: SortingInteractor
    private lateinit var repository: TestSortingRepository
    private lateinit var mapper: SortingDomain.Mapper<ItemsUi>
    private lateinit var cache: TestSortCacheDataSource
    private lateinit var changeSort: TestChangeSort

    @Before
    fun init() {
        repository = TestSortingRepository()
        cache = TestSortCacheDataSource()
        changeSort = TestChangeSort()
        mapper = SortingDomain.Mapper.Base(cache, changeSort)
        interactor = SortingInteractor.Base(mapper, repository, TestDispatchers(), TestHandleError())
    }

    @Test
    fun `test sorting`() = runBlocking {
        var actual: ItemsUi = ItemsUi.Base(emptyList())
        interactor.sorting { actual = it }

        val expected = ItemsUi.Base(listOf(
            SortingUi("by name", "by name", false, changeSort)
        ))

        assertEquals(expected, actual)
        assertEquals(1, repository.sortingCalledCount)
    }

    @Test
    fun `information about some sorting ui`() = runBlocking {
        var actual: ItemsUi = ItemsUi.Base(emptyList())
        repository.changeResult(SortingDomain.Base(listOf("by name", "by value")))

        interactor.sorting { actual = it }

        val expected = ItemsUi.Base(listOf(
            SortingUi("by name", "by name", false, changeSort),
            SortingUi("by value", "by value", false, changeSort)
        ))

        assertEquals(expected, actual)
        assertEquals(1, repository.sortingCalledCount)
    }
}