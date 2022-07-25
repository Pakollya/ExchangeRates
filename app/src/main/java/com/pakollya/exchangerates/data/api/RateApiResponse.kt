package com.pakollya.exchangerates.data.api

data class RateApiResponse(
    val success: Boolean,
    val base: String,
    val rates: String
)

data class SymbolApiResponse(
    val success: Boolean,
    val symbols: String
)