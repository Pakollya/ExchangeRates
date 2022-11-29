package com.pakollya.exchangerates.sorting.presentation

import com.pakollya.exchangerates.currencies.presentation.BaseTest
import com.pakollya.exchangerates.names.presentation.ItemsUi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SortingViewModelTest : BaseTest() {

    private lateinit var viewModel: SortingViewModel
    private lateinit var interactor: TestSortingInteractor
    private lateinit var communications: TestSortingCommunications
    private lateinit var changeSort: TestChangeSort

    @Before
    fun init() {
        interactor = TestSortingInteractor()
        communications = TestSortingCommunications()
        viewModel = SortingViewModel(interactor, communications, TestDispatchers())
        changeSort = TestChangeSort()
    }

    @Test
    fun `test init`() = runBlocking {

        viewModel.init(isFirstRun = true)

        assertEquals(1, interactor.sortingCalledList.size)
        assertEquals(1, communications.sortingList.size)
        assertEquals(1, communications.timesList)
    }

    @Test
    fun `test show sorting`() = runBlocking {

        viewModel.showSorting()

        assertEquals(1, interactor.sortingCalledList.size)
        assertEquals(1, communications.sortingList.size)
        assertEquals(1, communications.timesList)
    }

    @Test
    fun `information about some sorting`() = runBlocking {

        interactor.changeResult(ItemsUi.Base(listOf(
            SortingUi("by name", "by name", true, changeSort),
            SortingUi("by value", "by value", false, changeSort)
        )))

        viewModel.showSorting()

        assertEquals(1, interactor.sortingCalledList.size)
        assertEquals(1, communications.sortingList.size)
        assertEquals(1, communications.timesList)

        val actual = interactor.sortingCalledList[0]
        val expected = ItemsUi.Base(listOf(
            SortingUi("by name", "by name", true, changeSort),
            SortingUi("by value", "by value", false, changeSort)
        ))

        assertEquals(expected, actual)
    }
}