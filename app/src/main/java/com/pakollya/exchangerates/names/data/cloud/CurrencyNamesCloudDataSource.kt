package com.pakollya.exchangerates.names.data.cloud

import com.pakollya.exchangerates.base.data.CloudDataSource
import com.pakollya.exchangerates.base.data.HandleError

interface CurrencyNamesCloudDataSource {

    suspend fun names(): CurrencyNameCloud.CurrencyNames

    class Base(
        private val currencyNameService: CurrencyNamesService,
        handleError: HandleError
    ) : CloudDataSource.Abstract(handleError), CurrencyNamesCloudDataSource {

        override suspend fun names() = handle {
            currencyNameService.names(API_KEY)
        }

        companion object {
            private const val API_KEY = "gDxl8nyCRBj5yZbmez8pPppgNVxYxeQi"
        }
    }
}