package com.pakollya.exchangerates.currencies.data

import com.pakollya.exchangerates.currencies.data.cache.CurrenciesCache
import com.pakollya.exchangerates.currencies.data.cache.CurrenciesCache.Currencies
import com.pakollya.exchangerates.currencies.data.cache.CurrenciesCacheDataSource
import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesCloud
import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesCloudDataSource
import com.pakollya.exchangerates.currencies.domain.CommonCurrencyRepository
import com.pakollya.exchangerates.currencies.domain.CurrenciesDomain
import com.pakollya.exchangerates.favorites.data.FavoriteMapper
import com.pakollya.exchangerates.sorting.data.SortCacheDataSource
import com.pakollya.exchangerates.sorting.domain.Sorting.Companion.sorting
import com.pakollya.exchangerates.utils.EUR

class BaseCurrencyRepository(
    private val baseCurrencyCache: BaseCurrencyCacheDataSource,
    private val sortCacheDataSource: SortCacheDataSource,
    private val currenciesCache: CurrenciesCacheDataSource,
    private val currenciesCloudDataSource: ExchangeRatesCloudDataSource,
    private val mapper: ExchangeRatesCloud.Mapper<Currencies>,
    private val favoriteMapper: FavoriteMapper,
    private val currenciesMapper: CurrenciesCache.Mapper<CurrenciesDomain>
) : CommonCurrencyRepository {

    override suspend fun currencies(): CurrenciesDomain {
        return currenciesCache().map(currenciesMapper)
    }

    override suspend fun favorites(): CurrenciesDomain {
        return currenciesCache().map(favoriteMapper)
    }

    private fun sortedCurrencies(): Currencies {
        val currencies = currenciesCache.currencyDao().currencies(baseCurrency())
        return currencies.sort(sorting(sortCacheDataSource.read()))
    }

    private suspend fun updateCurrencies() {
        val currencies = currenciesCloudDataSource.exchangeRates(baseCurrency())
        currenciesCache.currencyDao().insertCurrencies(currencies.map(mapper))
    }

    private fun baseCurrency() = baseCurrencyCache.read().ifEmpty { EUR }

    private fun currenciesExist() = currenciesCache.currencyDao().currenciesExist(baseCurrency())

    private suspend fun currenciesCache(): Currencies {
        if (currenciesExist()) {
            val currencies = sortedCurrencies()
            if (currencies.isValid()) {
                return currencies
            }
        }

        updateCurrencies()
        return sortedCurrencies()
    }
}