package com.pakollya.exchangerates.currencies.domain

import com.pakollya.exchangerates.base.presentation.ItemUi
import com.pakollya.exchangerates.currencies.data.cache.Currency
import com.pakollya.exchangerates.currencies.presentation.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrenciesInteractorTest : BaseTest() {

    private lateinit var interactor: CurrenciesInteractor
    private lateinit var repository: TestCurrenciesRepository
    private lateinit var cache: TestFavoritesCacheDataSource
    private lateinit var changeFavorite: TestChangeFavorite

    @Before
    fun init() {
        repository = TestCurrenciesRepository()
        cache = TestFavoritesCacheDataSource()
        changeFavorite = TestChangeFavorite()
        interactor = CurrenciesInteractor.Base(
            CurrenciesDomain.Mapper.Base(cache, changeFavorite),
            repository,
            TestDispatchers(),
            TestHandleError()
        )
    }

    @Test
    fun `test currencies`() = runBlocking {
        var actual: CurrenсiesUi = CurrenсiesUi.Base(emptyList(), BaseCurrencyUi())
        interactor.currencies { actual = it }

        val expected = CurrenсiesUi.Base(
            listOf<ItemUi>(DateUi("Last update: 2022")),
            BaseCurrencyUi("USD")
        )

        assertEquals(expected, actual)
        assertEquals(1, repository.currenciesCalledCount)
    }

    @Test
    fun `information about some currencies ui`() = runBlocking {
        var actual: CurrenсiesUi = CurrenсiesUi.Base(emptyList(), BaseCurrencyUi())
        repository.changeResult(
            CurrenciesDomain.Base(
                "BTC",
                "2020",
                listOf(Currency("AOA", 0.1234))
            )
        )

        interactor.currencies { actual = it }

        val expected = CurrenсiesUi.Base(listOf(
            DateUi("Last update: 2020"),
            CurrencyUi("AOA", "AOA", "0.1234", true, changeFavorite)),
            BaseCurrencyUi("BTC")
        )

        assertEquals(expected, actual)
        assertEquals(1, repository.currenciesCalledCount)
    }
}