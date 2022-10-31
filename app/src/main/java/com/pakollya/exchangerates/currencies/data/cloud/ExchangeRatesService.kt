package com.pakollya.exchangerates.currencies.data.cloud

import com.pakollya.exchangerates.currencies.data.cloud.ExchangeRatesCloud.ExchangeRates
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ExchangeRatesService {

    @GET(REQUEST_LATEST)
    suspend fun exchangeRates(
        @Header(API_KEY_HEADER) apiKey: String
    ): ExchangeRates

    @GET(REQUEST_LATEST)
    suspend fun exchangeRates(
        @Header(API_KEY_HEADER) apiKey: String,
        @Query(BASE) base: String
    ): ExchangeRates

    companion object {
        private const val REQUEST_LATEST = "latest"
        private const val API_KEY_HEADER = "apikey"
        private const val BASE = "base"
    }
}