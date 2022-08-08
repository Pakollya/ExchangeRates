package com.pakollya.exchangerates.data.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import com.pakollya.exchangerates.data.api.RateApiService
import com.pakollya.exchangerates.data.database.CacheDataSource.BaseDataSource
import com.pakollya.exchangerates.data.database.currency.Currency
import com.pakollya.exchangerates.data.database.favorite.CurrencyFavorite
import com.pakollya.exchangerates.data.database.name.CurrencyName
import com.pakollya.exchangerates.presentation.list.SortedByType
import com.pakollya.exchangerates.presentation.list.SortedByType.*
import com.pakollya.exchangerates.utils.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val apiService: RateApiService,
    private val appDatabase: BaseDataSource
) {
    private suspend fun getApiResponse() = apiService.rateApiResponse()

    private suspend fun getApiResponse(name: String) = apiService.rateApiResponse(name)

    suspend fun getRateApiResponse(name: String? = null): String? {
        val response = if (name.isNullOrEmpty()) getApiResponse() else getApiResponse(name)
        return response.ifEmpty { null }
    }

    suspend fun getSymbolApiResponse(): String? {
        val response = apiService.symbolApiResponse()
        return response.ifEmpty { null }
    }

    fun insertCurrencyList(currencyList: List<Currency>?) {
        appDatabase.currencyListDao().insertCurrencyList(currencyList)
    }

    fun currencyListSortedBy(sortedByType: SortedByType?, isFavorite: Boolean = false): List<Currency>? {
        var query = SELECT_FROM_CURRENCY_LIST

        if (isFavorite) {
            query += WHERE_EXISTS_CURRENCY_FAVORITE
        }

        sortedByType?.let { sort ->
            query += when(sort) {
                SORTED_BY_NAME -> ORDER_BY_NAME
                SORTED_BY_NAME_DESC -> ORDER_BY_NAME_DESC
                SORTED_BY_VALUE -> ORDER_BY_VALUE
                SORTED_BY_VALUE_DESC -> ORDER_BY_VALUE_DESC
            }
        }

        val simpleQuery = SimpleSQLiteQuery(query)

        return appDatabase.currencyListDao().currencyList(simpleQuery)
    }

    fun insertCurrencyNameList(nameList: List<CurrencyName>?) {
        appDatabase.currencyNameDao().insertCurrencyNameList(nameList)
    }

    fun currencyNameList(): Flow<List<CurrencyName>?> {
        return appDatabase.currencyNameDao().currencyNameList()
    }

    fun insertCurrency(name: String) {
        appDatabase.currencyFavoriteDao().insertCurrencyFavorite(CurrencyFavorite(name))
    }

    fun deleteCurrencyFavoriteByName(name: String) {
        appDatabase.currencyFavoriteDao().deleteCurrencyFavoriteByName(name)
    }

    fun hasCurrencyFavoriteByName(name: String): Boolean {
        return appDatabase.currencyFavoriteDao().hasCurrencyFavoriteByName(name)
    }

    fun getSortedByType() = appDatabase.currencySettingsDao().getSortedByType()

    fun updateSortedByType(sortedByType: SortedByType?) {
        appDatabase.currencySettingsDao().updateSortedByType(sortedByType)
    }

    fun getBaseName(): Flow<String?> = appDatabase.currencySettingsDao().getBaseName()

    fun updateBaseName(baseName: String?) = appDatabase.currencySettingsDao().updateBaseName(baseName)

    fun hasCurrencySettings() = appDatabase.currencySettingsDao().hasCurrencySettings()

    fun setDefaultSettings() = appDatabase.currencySettingsDao().setDefaultSettings()
}