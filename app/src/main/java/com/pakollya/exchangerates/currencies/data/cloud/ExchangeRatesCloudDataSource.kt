package com.pakollya.exchangerates.currencies.data.cloud

import com.pakollya.exchangerates.base.data.CloudDataSource
import com.pakollya.exchangerates.base.data.HandleError

interface ExchangeRatesCloudDataSource {

    suspend fun exchangeRates(base: String?): ExchangeRatesCloud.ExchangeRates

    class Base(
        private val currencyService: ExchangeRatesService,
        handleError: HandleError
    ) : CloudDataSource.Abstract(handleError), ExchangeRatesCloudDataSource {
        override suspend fun exchangeRates(base: String?) = handle {
            if (base.isNullOrEmpty())
                currencyService.exchangeRates(API_KEY)
            else
                currencyService.exchangeRates(API_KEY, base)
        }

        companion object {
            private const val API_KEY = "gDxl8nyCRBj5yZbmez8pPppgNVxYxeQi"
        }
    }
}