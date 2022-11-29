package com.pakollya.exchangerates.currencies.data

import com.pakollya.exchangerates.base.domain.NoInternetConnectionException
import com.pakollya.exchangerates.currencies.data.cache.BaseCurrencyCacheDataSource
import com.pakollya.exchangerates.currencies.data.cache.CurrenciesCacheDataSource
import com.pakollya.exchangerates.currencies.data.cache.Currency
import com.pakollya.exchangerates.currencies.data.cache.CurrencyCache.Currencies
import com.pakollya.exchangerates.currencies.data.cache.CurrencyCache.Mapper
import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesCloud
import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesCloud.ExchangeRates
import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesCloudDataSource
import com.pakollya.exchangerates.currencies.domain.CurrenciesDomain
import com.pakollya.exchangerates.currencies.domain.CurrenciesRepository
import com.pakollya.exchangerates.sorting.data.SortCacheDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class BaseCurrenciesRepositoryTest {

    private lateinit var repository: CurrenciesRepository
    private lateinit var baseCache: TestBaseCurrencyCacheDataSource
    private lateinit var sortCache: TestSortCacheDataSource
    private lateinit var cache: TestCurrenciesCacheDataSource
    private lateinit var cloud: TestExchangeRatesCloudDataSource
    private lateinit var mapper: Mapper<CurrenciesDomain>
    private lateinit var dateFormat: SimpleDateFormat
    private lateinit var date: String

    @Before
    fun init() {
        baseCache = TestBaseCurrencyCacheDataSource()
        sortCache = TestSortCacheDataSource()
        cache = TestCurrenciesCacheDataSource()
        cloud = TestExchangeRatesCloudDataSource()
        mapper = Mapper.Base()
        repository = BaseCurrenciesRepository(baseCache, sortCache, cache, cloud, mapper)

        dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        date = dateFormat.format(Date()).toString()
    }

    @Test
    fun `test currencies`() = runBlocking {
        baseCache.replaceData("USD")
        cache.replaceData(Currencies(
            "USD", date, listOf(
                Currency("EUR", 0.1234),
                Currency("USD", 0.4321)
            )
        ))

        val actual = repository.currencies()

        val expected = CurrenciesDomain.Base(
            "USD", date, listOf(
                Currency("EUR", 0.1234),
                Currency("USD", 0.4321)
            )
        )

        assertEquals(expected, actual)
        assertEquals(1, cache.currenciesCalledCount)
        assertEquals(0, cache.saveCurrenciesCalledCount)
        assertEquals(0, cloud.exchangeRatesCalledCount)
        assertEquals(1, sortCache.sortingCacheCalledCount)
        assertEquals(1, baseCache.baseCurrencyCalled.size)
    }

    @Test
    fun `test currencies cache not valid`() = runBlocking {
        baseCache.replaceData("USD")
        cache.replaceData(Currencies(
            "USD", "2000-01-01",
            listOf(Currency("EUR", 0.1234))
        ))
        cloud.replaceData(ExchangeRates("USD", date, mapOf(Pair("EUR", 0.1234))))

        val actual = repository.currencies()
        val expected = CurrenciesDomain.Base(
            "USD", date, listOf(
                Currency("EUR", 0.1234)
            )
        )

        assertEquals(expected, actual)
        assertEquals(2, cache.currenciesCalledCount)
        assertEquals(1, cache.saveCurrenciesCalledCount)
        assertEquals(1, cloud.exchangeRatesCalledCount)
        assertEquals(1, sortCache.sortingCacheCalledCount)
        assertEquals(1, baseCache.baseCurrencyCalled.size)
        assertEquals(Currencies("USD", date,
            listOf(Currency("EUR", 0.1234))),
            cache.data[0])
    }

    @Test
    fun `test currencies cache is empty`() = runBlocking {
        baseCache.replaceData("EUR")
        cache.replaceData(Currencies("EUR", date, emptyList()))
        cloud.replaceData(ExchangeRates("EUR", date, mapOf(Pair("BTC", 0.4321))))

        val actual = repository.currencies()
        val expected = CurrenciesDomain.Base(
            "EUR", date, listOf(
                Currency("BTC", 0.4321)
            )
        )

        assertEquals(expected, actual)
        assertEquals(2, cache.currenciesCalledCount)
        assertEquals(1, cache.saveCurrenciesCalledCount)
        assertEquals(1, cloud.exchangeRatesCalledCount)
        assertEquals(1, sortCache.sortingCacheCalledCount)
        assertEquals(1, baseCache.baseCurrencyCalled.size)
        assertEquals(Currencies("EUR", date,
            listOf(Currency("BTC", 0.4321))),
            cache.data[0])
    }


    @Test
    fun `test currencies cache is empty and not valid`() = runBlocking {
        baseCache.replaceData("AOA")
        cache.replaceData(Currencies("AOA", "2000-01-01", emptyList()))
        cloud.replaceData(ExchangeRates("AOA", date, mapOf(
            Pair("BTC", 0.4321),
            Pair("EUR", 0.1234)
        )))

        val actual = repository.currencies()
        val expected = CurrenciesDomain.Base(
            "AOA", date, listOf(
                Currency("BTC", 0.4321),
                Currency("EUR", 0.1234)
            )
        )

        assertEquals(expected, actual)
        assertEquals(2, cache.currenciesCalledCount)
        assertEquals(1, cache.saveCurrenciesCalledCount)
        assertEquals(1, cloud.exchangeRatesCalledCount)
        assertEquals(1, sortCache.sortingCacheCalledCount)
        assertEquals(1, baseCache.baseCurrencyCalled.size)
        assertEquals(Currencies("AOA", date,
            listOf(
                Currency("BTC", 0.4321),
                Currency("EUR", 0.1234)
            )), cache.data[0])
    }

    @Test(expected = NoInternetConnectionException::class)
    fun `test currencies cached failure`() = runBlocking {
        cloud.changeConnection(false)
        baseCache.replaceData("")
        cache.replaceData(Currencies("", "", emptyList()))

        repository.currencies()

        assertEquals(1, cache.currenciesCalledCount)
        assertEquals(0, cache.saveCurrenciesCalledCount)
        assertEquals(1, cloud.exchangeRatesCalledCount)
        assertEquals(0, sortCache.sortingCacheCalledCount)
        assertEquals(1, baseCache.baseCurrencyCalled.size)
    }

    @Test
    fun `test base currency`() {
        baseCache.replaceData("BTC")

        val actual = repository.baseCurrency()
        val expected = "BTC"

        assertEquals(actual, expected)
        assertEquals(0, baseCache.changeBaseCalledCount)
        assertEquals(1, baseCache.baseCurrencyCalled.size)
        assertEquals("BTC", baseCache.baseCurrencyCalled[0])
    }

    @Test
    fun `test base currency is empty`() {
        baseCache.replaceData("")

        val actual = repository.baseCurrency()
        val expected = "EUR"

        assertEquals(actual, expected)
        assertEquals(1, baseCache.changeBaseCalledCount)
        assertEquals(1, baseCache.baseCurrencyCalled.size)
        assertEquals("EUR", baseCache.baseCurrencyCalled[0])
    }

    private class TestBaseCurrencyCacheDataSource : BaseCurrencyCacheDataSource {
        val baseCurrencyCalled = mutableListOf<String>()
        var changeBaseCalledCount = 0

        fun replaceData(newData: String) {
            baseCurrencyCalled.clear()
            baseCurrencyCalled.add(newData)
        }

        override fun changeBase(base: String) {
            changeBaseCalledCount++
            baseCurrencyCalled[0] = base
        }

        override fun read(): String {
            return baseCurrencyCalled[0]
        }

        override fun isBase(base: String) = true
    }

    private class TestSortCacheDataSource : SortCacheDataSource {
        private var sortingCache = ""

        var sortingCacheCalledCount = 0

        override fun changeSort(sorting: String) {
            sortingCache = sorting
        }

        override fun read(): String {
            sortingCacheCalledCount++
            return sortingCache
        }

        override fun isSorting(sorting: String) = false
    }

    private class TestCurrenciesCacheDataSource : CurrenciesCacheDataSource {

        var currenciesCalledCount = 0
        var saveCurrenciesCalledCount = 0

        var data = mutableListOf<Currencies>()

        fun replaceData(newData: Currencies) {
            data.clear()
            data.add(newData)
        }

        override suspend fun currencies(base: String): Currencies {
            currenciesCalledCount++
            return data[0]
        }

        override suspend fun saveCurrencies(rates: ExchangeRates) {
            saveCurrenciesCalledCount++
            data.clear()
            data.add(rates.map(ExchangeRatesCloud.Mapper.Base()))
        }
    }

    private class TestExchangeRatesCloudDataSource : ExchangeRatesCloudDataSource {

        private var connection = true
        private var data = mutableListOf<ExchangeRates>()

        var exchangeRatesCalledCount = 0

        fun changeConnection(connected: Boolean) {
            connection = connected
        }

        fun replaceData(newData: ExchangeRates) {
            data.clear()
            data.add(newData)
        }

        override suspend fun exchangeRates(base: String?): ExchangeRates {
            exchangeRatesCalledCount++
            return if (connection) data[0] else throw NoInternetConnectionException()
        }
    }
}