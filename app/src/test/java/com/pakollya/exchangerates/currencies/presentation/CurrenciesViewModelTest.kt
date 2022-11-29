package com.pakollya.exchangerates.currencies.presentation

import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrenciesViewModelTest : BaseTest() {

    private lateinit var viewModel: CurrenciesViewModel
    private lateinit var communications: TestCurrenciesCommunications
    private lateinit var interactor: TestCurrenciesInteractor
    private lateinit var dispatchers: TestDispatchers
    private lateinit var changeFavorite: TestChangeFavorite

    @Before
    fun init() {
        communications = TestCurrenciesCommunications()
        interactor = TestCurrenciesInteractor()
        dispatchers = TestDispatchers()
        viewModel = CurrenciesViewModel(interactor, communications, dispatchers)
        changeFavorite = TestChangeFavorite()
    }

    /**
     * Initial test
     * At start get data and show it
     */

    @Test
    fun `test init`() = runBlocking {

        viewModel.init(isFirstRun = true)

        assertEquals(VISIBLE, communications.progressList[0])
        assertEquals(1, interactor.currenciesCalledList.size)

        assertEquals(2, communications.progressList.size)
        assertEquals(GONE, communications.progressList[1])

        assertEquals(1, communications.navigationList.size)
        assertEquals(VISIBLE, communications.navigationList[0])

        assertEquals(1, communications.currenciesList.size)
        assertEquals(1, communications.timesList)
    }

    /**
     * Try to get some data and show it
     */
    @Test
    fun `test show currencies`() = runBlocking {

        viewModel.showCurrencies()

        assertEquals(1, communications.progressList.size)
        assertEquals(GONE, communications.progressList[0])
        assertEquals(1, interactor.currenciesCalledList.size)

        assertEquals(0, communications.navigationList.size)

        assertEquals(1, communications.currenciesList.size)
        assertEquals(1, communications.timesList)
    }

    /**
     * Try to get information about some currency
     */
    @Test
    fun `information about some currency`() = runBlocking {
        interactor.changeResult(CurrenсiesUi.Base(
            listOf(CurrencyUi("EUR", "EUR", "60", true, changeFavorite)),
            BaseCurrencyUi("USD"))
        )
        viewModel.showCurrencies()

        assertEquals(1, communications.progressList.size)
        assertEquals(GONE, communications.progressList[0])
        assertEquals(1, interactor.currenciesCalledList.size)
        assertEquals(1, communications.timesList)

        val actual = interactor.currenciesCalledList[0]
        val expected = CurrenсiesUi.Base(
            listOf(CurrencyUi("EUR", "EUR", "60", true, changeFavorite)),
            BaseCurrencyUi("USD"))

        assertEquals(expected, actual)
    }
}