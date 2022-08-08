package com.pakollya.exchangerates.data.api

class RateApiService(
    private val api: Api,
    private val apiKey: String
) {
    suspend fun rateApiResponse(): String = api.getRateList(apiKey)

    suspend fun rateApiResponse(base: String): String = api.getRateList(apiKey, base)

    suspend fun symbolApiResponse(): String = api.getSymbolList(apiKey)
}