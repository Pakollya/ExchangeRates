package com.pakollya.exchangerates.currencies.data.cloud

import com.pakollya.exchangerates.base.data.MakeService
import com.pakollya.exchangerates.base.data.RetrofitBuilder
import com.pakollya.exchangerates.utils.BASE_URL

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
    }
}