package com.pakollya.exchangerates.names.data.cloud

import com.pakollya.exchangerates.base.data.MakeService
import com.pakollya.exchangerates.base.data.RetrofitBuilder

interface CurrencyNameApiService {

    fun currencyNamesService(): CurrencyNamesService

    class Base(
        retrofitBuilder: RetrofitBuilder,
    ) : MakeService.Abstract(
        retrofitBuilder
    ), CurrencyNameApiService {

        override fun baseUrl(): String = BASE_URL

        override fun currencyNamesService(): CurrencyNamesService =
            service(CurrencyNamesService::class.java)

        companion object {
            private const val BASE_URL = "https://api.apilayer.com/exchangerates_data/"
        }
    }
}