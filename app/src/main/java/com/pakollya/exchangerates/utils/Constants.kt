package com.pakollya.exchangerates.utils

const val API_KEY = "gDxl8nyCRBj5yZbmez8pPppgNVxYxeQi"

const val BASE_URL = "https://api.apilayer.com/exchangerates_data/"

const val REQUEST_LATEST = "latest"

const val REQUEST_SYMBOLS = "symbols"

const val DATABASE_NAME = "currency_rate"

const val API_KEY_HEADER = "apikey"

const val BASE = "base"

const val CURRENCY_LIST = "currency_list"

const val CURRENCY_NAME = "currency_name"

const val CURRENCY_FAVORITE = "currency_favorite"

const val SETTINGS = "settings"

const val NAME = "name"

const val FULL_NAME = "fullName"

const val SELECT_FROM_CURRENCY_LIST = "SELECT * FROM currency_list"

const val WHERE_EXISTS_CURRENCY_FAVORITE = " WHERE EXISTS(SELECT * FROM currency_favorite WHERE currency_favorite.name = currency_list.name)"

const val ORDER_BY_NAME = " ORDER BY currency_list.name"

const val ORDER_BY_NAME_DESC = " ORDER BY currency_list.name DESC"

const val ORDER_BY_VALUE = " ORDER BY currency_list.value"

const val ORDER_BY_VALUE_DESC = " ORDER BY currency_list.value DESC"

const val USD = "USD"