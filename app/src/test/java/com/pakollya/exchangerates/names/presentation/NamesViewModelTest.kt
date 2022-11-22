package com.pakollya.exchangerates.names.presentation

import android.view.View.GONE
import android.view.View.VISIBLE
import com.pakollya.exchangerates.currencies.presentation.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NamesViewModelTest : BaseTest() {
    private lateinit var viewModel: NamesViewModel
    private lateinit var interactor: TestNamesInteractor
    private lateinit var communications: TestNamesCommunications
    private lateinit var dispatchers: TestDispatchers
    private lateinit var changeBaseCurrency: TestChangeBaseCurrency

    @Before
    fun init() {
        communications = TestNamesCommunications()
        interactor = TestNamesInteractor()
        dispatchers = TestDispatchers()
        viewModel = NamesViewModel(interactor, communications, dispatchers)
        changeBaseCurrency = TestChangeBaseCurrency()
    }

    @Test
    fun `test init`() = runBlocking {

        viewModel.init(isFirstRun = true)

        assertEquals(VISIBLE, communications.progressList[0])
        assertEquals(1, interactor.namesCalledList.size)

        assertEquals(2, communications.progressList.size)
        assertEquals(GONE, communications.progressList[1])

        assertEquals(1, communications.navigationList.size)
        assertEquals(GONE, communications.navigationList[0])

        assertEquals(1, communications.namesList.size)
        assertEquals(1, communications.timesList)
    }

    @Test
    fun `test show names`() = runBlocking {

        viewModel.showNames()

        assertEquals(1, communications.progressList.size)
        assertEquals(GONE, communications.progressList[0])
        assertEquals(1, interactor.namesCalledList.size)

        assertEquals(0, communications.navigationList.size)

        assertEquals(1, communications.namesList.size)
        assertEquals(1, communications.timesList)
    }

    @Test
    fun `information about some name`() = runBlocking {

        interactor.changeResult(ItemsUi.Base(listOf(
            CurrencyNameUi(
                "BTC", "BTC", "Bitcoin",
                true, changeBaseCurrency
            )
        )))

        viewModel.showNames()

        assertEquals(1, communications.progressList.size)
        assertEquals(GONE, communications.progressList[0])
        assertEquals(1, communications.namesList.size)
        assertEquals(1, communications.timesList)

        val actual = interactor.namesCalledList[0]
        val expected = ItemsUi.Base(listOf(
            CurrencyNameUi("BTC", "BTC", "Bitcoin",
                true, changeBaseCurrency)
        ))
        assertEquals(expected, actual)
    }
}