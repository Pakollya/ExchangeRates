package com.pakollya.exchangerates.names.domain

import com.pakollya.exchangerates.currencies.presentation.BaseTest
import com.pakollya.exchangerates.names.presentation.CurrencyNameUi
import com.pakollya.exchangerates.names.presentation.ItemsUi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NamesInteractorTest : BaseTest() {

    private lateinit var interactor: NamesInteractor
    private lateinit var repository: TestCurrencyNamesRepository
    private lateinit var cache: TestBaseCurrencyCacheDataSource
    private lateinit var changeBase: TestChangeBaseCurrency

    @Before
    fun init() {
        repository = TestCurrencyNamesRepository()
        cache = TestBaseCurrencyCacheDataSource()
        changeBase = TestChangeBaseCurrency()
        interactor = NamesInteractor.Base(
            CurrencyNameDomain.Mapper.Base(cache, changeBase),
            repository, TestDispatchers(), TestHandleError()
        )
    }

    @Test
    fun `test names`() = runBlocking {
        var actual: ItemsUi = ItemsUi.Base(emptyList())
        interactor.names { actual = it }

        val expected = ItemsUi.Base(listOf(
            CurrencyNameUi("BTC", "BTC", "Bitcoin", true, changeBase)))

        assertEquals(expected, actual)
        assertEquals(1, repository.namesCalledCount)
    }

    @Test
    fun `information about some name ui`() = runBlocking {
        var actual: ItemsUi = ItemsUi.Base(emptyList())
        repository.changeResult(CurrencyNameDomain.Base(
            listOf(
                Pair("EUR", "Euro"),
                Pair("USD", "United States dollar")
            ))
        )

        interactor.names { actual = it }

        val expected = ItemsUi.Base(listOf(
            CurrencyNameUi("EUR", "EUR", "Euro", true, changeBase),
            CurrencyNameUi("USD", "USD", "United States dollar", true, changeBase)
        ))

        assertEquals(expected, actual)
        assertEquals(1, repository.namesCalledCount)
    }
}