package com.pakollya.exchangerates.currencies.data

import com.pakollya.exchangerates.currencies.data.cache.CurrenciesCacheDataSource
import com.pakollya.exchangerates.currencies.data.cache.CurrencyCache
import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesCloudDataSource
import com.pakollya.exchangerates.currencies.domain.CurrenciesDomain
import com.pakollya.exchangerates.currencies.domain.CurrenciesRepository
import com.pakollya.exchangerates.sorting.data.SortCacheDataSource

class BaseCurrenciesRepository(
    private val baseCurrency: BaseCurrencyCacheDataSource,
    private val sort: SortCacheDataSource,
    private val currenciesCache: CurrenciesCacheDataSource,
    private val ratesCloud: ExchangeRatesCloudDataSource,
    private val mapper: CurrencyCache.Mapper<CurrenciesDomain>,
) : CurrenciesRepository {

    override suspend fun currencies(): CurrenciesDomain {
        val baseCurrency = baseCurrency()
        var currencies = currenciesCache.currencies(baseCurrency)

        if (currencies.isEmpty() || !currencies.isValid()) {
            val rates = ratesCloud.exchangeRates(baseCurrency)
            currenciesCache.saveCurrencies(rates)

            currencies = currenciesCache.currencies(baseCurrency)
        }

        return currencies
            .sort(sort.read())
            .map(mapper)
    }

    override fun baseCurrency() = baseCurrency.read().ifEmpty {
        baseCurrency.changeBase(EUR)
        EUR
    }

    companion object {
        private const val EUR = "EUR"
    }
}