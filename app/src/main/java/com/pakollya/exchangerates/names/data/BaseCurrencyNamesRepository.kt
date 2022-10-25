package com.pakollya.exchangerates.names.data

import com.pakollya.exchangerates.names.data.cache.CurrencyNamesCacheDataSource
import com.pakollya.exchangerates.names.data.cloud.CurrencyNameCloud
import com.pakollya.exchangerates.names.data.cloud.CurrencyNamesCloudDataSource
import com.pakollya.exchangerates.names.domain.CurrencyNameDomain
import com.pakollya.exchangerates.names.domain.CurrencyNamesRepository

class BaseCurrencyNamesRepository(
    private val namesCache: CurrencyNamesCacheDataSource,
    private val nameCloud: CurrencyNamesCloudDataSource,
    private val mapper: CurrencyNameCloud.Mapper<CurrencyNameDomain>,
) : CurrencyNamesRepository {

    override suspend fun names(): CurrencyNameDomain {
        var names = namesCache.names()
        if (names.isEmpty()) {
            namesCache.saveNames(nameCloud.names())
            names = namesCache.names()
        }

        return names.map(mapper)
    }
}