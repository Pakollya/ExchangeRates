package com.pakollya.exchangerates.favorites.presentation

import android.view.View
import com.pakollya.exchangerates.currencies.presentation.BaseCurrencyUi
import com.pakollya.exchangerates.currencies.presentation.BaseTest
import com.pakollya.exchangerates.currencies.presentation.CurrencyUi
import com.pakollya.exchangerates.currencies.presentation.CurrenсiesUi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FavoritesViewModelTest : BaseTest() {

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var communications: TestCurrenciesCommunications
    private lateinit var interactor: TestCurrenciesInteractor
    private lateinit var dispatchers: TestDispatchers
    private lateinit var changeFavorite: TestChangeFavorite

    @Before
    fun init() {
        communications = TestCurrenciesCommunications()
        interactor = TestCurrenciesInteractor()
        dispatchers = TestDispatchers()
        viewModel = FavoritesViewModel(interactor, communications, dispatchers)
        changeFavorite = TestChangeFavorite()
    }

    @Test
    fun `test init`() = runBlocking {

        viewModel.init(isFirstRun = true)

        assertEquals(View.VISIBLE, communications.progressList[0])
        assertEquals(1, interactor.currenciesCalledList.size)

        assertEquals(2, communications.progressList.size)
        assertEquals(View.GONE, communications.progressList[1])

        assertEquals(1, communications.navigationList.size)
        assertEquals(View.VISIBLE, communications.navigationList[0])

        assertEquals(1, communications.currenciesList.size)
        assertEquals(1, communications.timesList)
    }

    @Test
    fun `test show currencies`() = runBlocking {

        viewModel.showCurrencies()

        assertEquals(1, communications.progressList.size)
        assertEquals(View.GONE, communications.progressList[0])
        assertEquals(1, interactor.currenciesCalledList.size)

        assertEquals(0, communications.navigationList.size)

        assertEquals(1, communications.currenciesList.size)
        assertEquals(1, communications.timesList)
    }

    @Test
    fun `information about some currency`() = runBlocking {
        interactor.changeResult(CurrenсiesUi.Base(
            listOf(CurrencyUi("EUR", "EUR", "60", true, changeFavorite)),
            BaseCurrencyUi("USD"))
        )
        viewModel.showCurrencies()

        assertEquals(1, communications.progressList.size)
        assertEquals(View.GONE, communications.progressList[0])
        assertEquals(1, interactor.currenciesCalledList.size)
        assertEquals(1, communications.timesList)

        val actual = interactor.currenciesCalledList[0]
        val expected = CurrenсiesUi.Base(
            listOf(CurrencyUi("EUR", "EUR", "60", true, changeFavorite)),
            BaseCurrencyUi("USD"))

        assertEquals(expected, actual)
    }
}