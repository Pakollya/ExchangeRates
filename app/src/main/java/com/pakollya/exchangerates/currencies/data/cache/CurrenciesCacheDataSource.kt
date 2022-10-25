package com.pakollya.exchangerates.currencies.data.cache

import com.pakollya.exchangerates.currencies.data.cache.CurrencyCache.Currencies
import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesCloud
import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesCloud.ExchangeRates
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface CurrenciesCacheDataSource {

    suspend fun currencies(base: String): Currencies

    suspend fun saveCurrencies(rates: ExchangeRates)

    class Base(
        private val dao: CurrenciesDao,
        private val mapper: ExchangeRatesCloud.Mapper<Currencies>,
    ) : CurrenciesCacheDataSource {

        private val mutex = Mutex()

        override suspend fun currencies(base: String): Currencies = mutex.withLock {
            return if (dao.currenciesExist(base))
                dao.currencies(base)
            else
                Currencies("", "", emptyList())
        }

        override suspend fun saveCurrencies(rates: ExchangeRates) = mutex.withLock {
            dao.insertCurrencies(rates.map(mapper))
        }
    }
}