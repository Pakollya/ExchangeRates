package com.pakollya.exchangerates.currencies.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.base.data.HandleError
import com.pakollya.exchangerates.base.domain.Interactor
import com.pakollya.exchangerates.currencies.data.cache.BaseCurrencyCacheDataSource
import com.pakollya.exchangerates.currencies.domain.CurrenciesDomain
import com.pakollya.exchangerates.currencies.domain.CurrenciesInteractor
import com.pakollya.exchangerates.currencies.domain.CurrenciesRepository
import com.pakollya.exchangerates.favorites.data.FavoritesCacheDataSource
import com.pakollya.exchangerates.favorites.presentation.ChangeFavorite
import com.pakollya.exchangerates.names.domain.CurrencyNameDomain
import com.pakollya.exchangerates.names.domain.CurrencyNamesRepository
import com.pakollya.exchangerates.names.domain.NamesInteractor
import com.pakollya.exchangerates.names.presentation.ChangeBaseCurrency
import com.pakollya.exchangerates.names.presentation.ItemsUi
import com.pakollya.exchangerates.names.presentation.NamesCommunications
import com.pakollya.exchangerates.sorting.data.SortCacheDataSource
import com.pakollya.exchangerates.sorting.domain.SortingDomain
import com.pakollya.exchangerates.sorting.domain.SortingInteractor
import com.pakollya.exchangerates.sorting.domain.SortingRepository
import com.pakollya.exchangerates.sorting.presentation.ChangeSort
import com.pakollya.exchangerates.sorting.presentation.SortingCommunications
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

abstract class BaseTest {

    protected class TestCurrenciesCommunications : CurrenciesCommunications {

        val progressList = mutableListOf<Int>()
        val navigationList = mutableListOf<Int>()
        val currenciesList = mutableListOf<CurrenсiesUi>()
        var timesList = 0

        override fun showNavigation(show: Int) {
            navigationList.add(show)
        }

        override fun showProgress(show: Int) {
            progressList.add(show)
        }

        override fun showCurrencies(currencies: CurrenсiesUi) {
            timesList++
            currenciesList.add(currencies)
        }

        override fun observeError(owner: LifecycleOwner, observer: Observer<String>) = Unit
        override fun observeList(owner: LifecycleOwner, observer: Observer<CurrenсiesUi>) = Unit
        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) = Unit
        override fun observeSorting(owner: LifecycleOwner, observer: Observer<Boolean>) = Unit
    }

    protected class TestNamesCommunications : NamesCommunications {

        val progressList = mutableListOf<Int>()
        val navigationList = mutableListOf<Int>()
        val namesList = mutableListOf<ItemsUi>()
        var timesList = 0

        override fun showNavigation(show: Int) {
            navigationList.add(show)
        }

        override fun showProgress(show: Int) {
            progressList.add(show)
        }

        override fun showNames(names: ItemsUi) {
            timesList++
            namesList.add(names)
        }

        override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) = Unit
        override fun observeBaseCurrency(owner: LifecycleOwner, observer: Observer<String>) = Unit
        override fun observeNames(owner: LifecycleOwner, observer: Observer<ItemsUi>) = Unit
    }

    protected class TestSortingCommunications : SortingCommunications {

        var timesList = 0
        val sortingList = mutableListOf<ItemsUi>()

        override fun showSorting(sorting: ItemsUi) {
            timesList++
            sortingList.add(sorting)
        }

        override fun observeSorting(owner: LifecycleOwner, observer: Observer<Boolean>) = Unit
        override fun observeSortingList(owner: LifecycleOwner, observer: Observer<ItemsUi>) = Unit
    }

    protected class TestCurrenciesInteractor : CurrenciesInteractor,
        Interactor.Abstract(TestDispatchers(), TestHandleError()) {

        private var result: CurrenсiesUi = CurrenсiesUi.Base(emptyList(), BaseCurrencyUi())

        var currenciesCalledList = mutableListOf<CurrenсiesUi>()

        fun changeResult(newResult: CurrenсiesUi) {
            result = newResult
        }

        override suspend fun currencies(
            successful: (CurrenсiesUi) -> Unit,
        ) = handle(successful) {
            currenciesCalledList.add(result)
            return@handle result
        }
    }

    protected class TestNamesInteractor : NamesInteractor,
        Interactor.Abstract(TestDispatchers(), TestHandleError()) {

        private var result: ItemsUi = ItemsUi.Base(emptyList())

        var namesCalledList = mutableListOf<ItemsUi>()

        fun changeResult(newResult: ItemsUi) {
            result = newResult
        }

        override suspend fun names(
            successful: (ItemsUi) -> Unit,
        ) = handle(successful) {
            namesCalledList.add(result)
            return@handle result
        }
    }

    protected class TestSortingInteractor : SortingInteractor,
        Interactor.Abstract(TestDispatchers(), TestHandleError()) {

        private var result: ItemsUi = ItemsUi.Base(emptyList())
        var sortingCalledList = mutableListOf<ItemsUi>()

        fun changeResult(newResult: ItemsUi) {
            result = newResult
        }

        override suspend fun sorting(successful: (ItemsUi) -> Unit) = handle(successful) {
            sortingCalledList.add(result)
            return@handle result
        }
    }

    protected class TestDispatchers(
        private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher(),
    ) : Dispatchers.Abstract(dispatcher, dispatcher)

    protected class TestHandleError : HandleError {
        override fun handle(error: Exception): Exception = error
    }

    protected class TestChangeFavorite : ChangeFavorite {
        override fun changeFavorite(id: String) = Unit
    }

    protected class TestCurrenciesRepository : CurrenciesRepository {

        private var result: CurrenciesDomain =
            CurrenciesDomain.Base("USD", "2022", emptyList())

        var currenciesCalledCount = 0

        fun changeResult(newResult: CurrenciesDomain) {
            result = newResult
        }

        override suspend fun currencies(): CurrenciesDomain {
            currenciesCalledCount++
            return result
        }

        override fun baseCurrency(): String = "USD"
    }

    protected class TestCurrencyNamesRepository : CurrencyNamesRepository {

        private var result: CurrencyNameDomain =
            CurrencyNameDomain.Base(listOf(Pair("BTC", "Bitcoin")))

        var namesCalledCount = 0

        fun changeResult(newResult: CurrencyNameDomain) {
            result = newResult
        }

        override suspend fun names(): CurrencyNameDomain {
            namesCalledCount++
            return result
        }
    }

    protected class TestSortingRepository : SortingRepository {

        private var result: SortingDomain = SortingDomain.Base(listOf("by name"))

        var sortingCalledCount = 0

        fun changeResult(newResult: SortingDomain) {
            result = newResult
        }

        override fun sorting(): SortingDomain {
            sortingCalledCount++
            return result
        }
    }

    protected class TestFavoritesCacheDataSource : FavoritesCacheDataSource {
        override fun changeFavorite(id: String) = Unit

        override fun isFavorite(id: String): Boolean = true
    }

    protected class TestChangeBaseCurrency : ChangeBaseCurrency {
        override fun changeBase(base: String) = Unit
    }

    protected class TestBaseCurrencyCacheDataSource : BaseCurrencyCacheDataSource {
        override fun changeBase(base: String) = Unit
        override fun read() = "USD"
        override fun isBase(base: String) = true
    }

    protected class TestSortCacheDataSource : SortCacheDataSource {
        override fun changeSort(sorting: String) = Unit
        override fun read() = "by value"
        override fun isSorting(sorting: String) = false
    }

    protected class TestChangeSort : ChangeSort {
        override fun changeSort(sorting: String) = Unit
    }
}