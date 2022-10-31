package com.pakollya.exchangerates.currencies.data.cloud

import com.pakollya.exchangerates.base.data.MakeService
import com.pakollya.exchangerates.base.data.RetrofitBuilder

interface ExchangeRatesApiService {

    fun exchangeRatesService(): ExchangeRatesService

    class Base(
        retrofitBuilder: RetrofitBuilder,
    ) : MakeService.Abstract(
        retrofitBuilder
    ), ExchangeRatesApiService {

        override fun baseUrl(): String = BASE_URL

        override fun exchangeRatesService(): ExchangeRatesService =
            service(ExchangeRatesService::class.java)

        companion object {
            private const val BASE_URL = "https://api.apilayer.com/exchangerates_data/"
        }
    }
}