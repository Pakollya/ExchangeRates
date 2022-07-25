package com.pakollya.exchangerates.data.api

import retrofit2.Response

class RateApiService(
    private val api: Api,
    private val apiKey: String
) {
    fun rateApiResponse(): Response<RateApiResponse> = api.getRateList(apiKey)

    fun rateApiResponse(base: String): Response<RateApiResponse> = api.getRateList(apiKey, base)

    fun symbolApiResponse(): Response<SymbolApiResponse> = api.getSymbolList(apiKey)
}