package com.pakollya.exchangerates.names.data.cloud

import com.pakollya.exchangerates.base.data.CloudDataSource
import com.pakollya.exchangerates.base.data.HandleError
import com.pakollya.exchangerates.utils.API_KEY

interface CurrencyNamesCloudDataSource {

    suspend fun names(): CurrencyNameCloud.CurrencyNames

    class Base(
        private val currencyNameService: CurrencyNamesService,
        handleError: HandleError
    ) : CloudDataSource.Abstract(handleError), CurrencyNamesCloudDataSource {

        override suspend fun names() = handle {
            currencyNameService.names(API_KEY)
        }
    }
}