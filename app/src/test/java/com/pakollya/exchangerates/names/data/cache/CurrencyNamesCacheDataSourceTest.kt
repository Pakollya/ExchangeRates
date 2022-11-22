package com.pakollya.exchangerates.names.data.cache

import com.pakollya.exchangerates.names.data.cloud.CurrencyNameCloud.CurrencyNames
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrencyNamesCacheDataSourceTest {

    private lateinit var dataSource: CurrencyNamesCacheDataSource
    private lateinit var dao: TestCurrencyNameDao

    @Before
    fun init() {
        dao = TestCurrencyNameDao()
        dataSource = CurrencyNamesCacheDataSource.Base(dao)
    }

    @Test
    fun `test names empty`() = runBlocking {
        dao.changeCount(0)

        val actual = dataSource.names()
        val expected = CurrencyNames(emptyMap())

        assertEquals(expected, actual)
    }

    @Test
    fun `test names not empty`() = runBlocking {
        dao.data.add(CurrencyNames(mutableMapOf(
            Pair("BTC", "Bitcoin"),
            Pair("EUR", "Euro")
        )))

        val actual = dataSource.names()
        val expected = CurrencyNames(mutableMapOf(
            Pair("BTC", "Bitcoin"),
            Pair("EUR", "Euro")
        ))

        assertEquals(expected, actual)
    }

    @Test
    fun `test save names`() = runBlocking {
        dataSource.saveNames(CurrencyNames(mutableMapOf(
            Pair("BTC", "Bitcoin"),
            Pair("EUR", "Euro")
        )))

        val actual = dao.data[0]
        val expected = CurrencyNames(mutableMapOf(
            Pair("BTC", "Bitcoin"),
            Pair("EUR", "Euro")
        ))

        assertEquals(expected, actual)
    }

    private class TestCurrencyNameDao : CurrencyNameDao {

        private var count = 1

        val data = mutableListOf<CurrencyNames>()

        fun changeCount(newCount: Int) {
            count = newCount
        }

        override fun names(): CurrencyNames = data[0]

        override fun insertNames(names: CurrencyNames?) {
            names?.let { data.add(it) }
        }

        override fun namesCount() = count
    }
}