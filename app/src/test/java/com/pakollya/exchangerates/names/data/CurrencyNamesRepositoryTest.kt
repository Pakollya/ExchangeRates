package com.pakollya.exchangerates.names.data

import com.pakollya.exchangerates.base.domain.NoInternetConnectionException
import com.pakollya.exchangerates.names.data.cache.CurrencyNamesCacheDataSource
import com.pakollya.exchangerates.names.data.cloud.CurrencyNameCloud.CurrencyNames
import com.pakollya.exchangerates.names.data.cloud.CurrencyNameCloud.Mapper
import com.pakollya.exchangerates.names.data.cloud.CurrencyNameCloud.Mapper.Base
import com.pakollya.exchangerates.names.data.cloud.CurrencyNamesCloudDataSource
import com.pakollya.exchangerates.names.domain.CurrencyNameDomain
import com.pakollya.exchangerates.names.domain.CurrencyNamesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrencyNamesRepositoryTest {

    private lateinit var repository: CurrencyNamesRepository
    private lateinit var cache: TestCurrencyNamesCacheDataSource
    private lateinit var cloud: TestCurrencyNamesCloudDataSource
    private lateinit var mapper: Mapper<CurrencyNameDomain>

    @Before
    fun init() {
        cache = TestCurrencyNamesCacheDataSource()
        cloud = TestCurrencyNamesCloudDataSource()
        mapper = Base()
        repository = BaseCurrencyNamesRepository(cache, cloud, mapper)
    }

    @Test
    fun `test names`() = runBlocking {
        cache.replaceData(CurrencyNames(
            mutableMapOf(
                Pair("BTC", "Bitcoin"),
                Pair("EUR", "Euro")
            )))

        val actual = repository.names()
        val expected = CurrencyNameDomain.Base(
            listOf(
                Pair("BTC", "Bitcoin"),
                Pair("EUR", "Euro")
            ))

        assertEquals(expected, actual)
        assertEquals(1, cache.namesCalledCount)
        assertEquals(0, cache.saveNamesCalledCount)
        assertEquals(0, cloud.namesCalledCount)
    }

    @Test
    fun `test names cache is empty`() = runBlocking {
        cache.replaceData(CurrencyNames(emptyMap()))
        cloud.replaceData(CurrencyNames(
            mutableMapOf(
                Pair("USD", "Dollar"),
                Pair("EUR", "Euro")
            )))

        val actual = repository.names()
        val expected = CurrencyNameDomain.Base(
            listOf(
                Pair("EUR", "Euro"),
                Pair("USD", "Dollar")
            ))

        assertEquals(expected, actual)
        assertEquals(2, cache.namesCalledCount)
        assertEquals(1, cache.saveNamesCalledCount)
        assertEquals(1, cloud.namesCalledCount)
        assertEquals(CurrencyNames(
            mutableMapOf(
                Pair("USD", "Dollar"),
                Pair("EUR", "Euro")
            )),
            cache.data[0]
        )
    }

    @Test(expected = NoInternetConnectionException::class)
    fun `test names cached failure`() = runBlocking {
        cloud.changeConnection(false)
        cache.replaceData(CurrencyNames(emptyMap()))

        repository.names()

        assertEquals(1, cache.namesCalledCount)
        assertEquals(0, cache.saveNamesCalledCount)
        assertEquals(1, cloud.namesCalledCount)
    }

    private class TestCurrencyNamesCacheDataSource : CurrencyNamesCacheDataSource {

        var namesCalledCount = 0
        var saveNamesCalledCount = 0

        var data = mutableListOf<CurrencyNames>()

        fun replaceData(newData: CurrencyNames) {
            data.clear()
            data.add(newData)
        }

        override suspend fun names(): CurrencyNames {
            namesCalledCount++
            return data[0]
        }

        override suspend fun saveNames(names: CurrencyNames) {
            saveNamesCalledCount++
            data.clear()
            data.add(names)
        }
    }

    private class TestCurrencyNamesCloudDataSource : CurrencyNamesCloudDataSource {

        private var connection = true
        private var data = mutableListOf<CurrencyNames>()

        var namesCalledCount = 0

        fun changeConnection(connected: Boolean) {
            connection = connected
        }

        fun replaceData(newData: CurrencyNames) {
            data.clear()
            data.add(newData)
        }

        override suspend fun names(): CurrencyNames {
            namesCalledCount++
            return if (connection) data[0] else throw NoInternetConnectionException()
        }
    }
}