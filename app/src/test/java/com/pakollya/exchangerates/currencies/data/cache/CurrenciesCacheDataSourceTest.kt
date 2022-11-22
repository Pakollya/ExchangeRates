package com.pakollya.exchangerates.currencies.data.cache

import com.pakollya.exchangerates.currencies.data.cache.CurrencyCache.Currencies
import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesCloud
import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesCloud.ExchangeRates
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrenciesCacheDataSourceTest {

    private lateinit var dataSource: CurrenciesCacheDataSource
    private lateinit var dao: TestDao
    private lateinit var mapper: ExchangeRatesCloud.Mapper<Currencies>

    @Before
    fun init() {
        dao = TestDao()
        mapper = ExchangeRatesCloud.Mapper.Base()
        dataSource = CurrenciesCacheDataSource.Base(dao, mapper)
    }

    @Test
    fun `test currencies empty`() = runBlocking {
        val actual = dataSource.currencies("")
        val expected = Currencies("", "", emptyList())

        assertEquals(expected, actual)
    }

    @Test
    fun `test currencies not empty`() = runBlocking {
        dao.data.add(Currencies("BTC", "2022-01-01",
            listOf(
                Currency("USD", 0.1234),
                Currency("EUR", 0.4321)
            )))

        val actual = dataSource.currencies("BTC")
        val expected = Currencies("BTC", "2022-01-01",
            listOf(
                Currency("USD", 0.1234),
                Currency("EUR", 0.4321)
            ))

        assertEquals(expected, actual)
    }

    @Test
    fun `test save currencies`() = runBlocking {
        dataSource.saveCurrencies(ExchangeRates(
            "USD", "2022-01-01",
            mapOf(Pair("EUR", 0.1234))))

        val actual = dao.data[0]
        val expected = Currencies("USD", "2022-01-01",
            listOf(Currency("EUR", 0.1234)))

        assertEquals(expected, actual)
    }

    private class TestDao : CurrenciesDao {

        val data = mutableListOf<Currencies>()

        override fun currencies(base: String): Currencies {
            val currencies = data.find { it.base == base }
            return if (currencies == null || currencies.isEmpty())
                Currencies("", "", emptyList())
            else
                currencies
        }

        override fun insertCurrencies(currencies: Currencies) {
            data.add(currencies)
        }

        override fun currenciesExist(base: String): Boolean {
            val currencies = data.find { it.base == base }
            return !(currencies == null || currencies.isEmpty())
        }
    }
}