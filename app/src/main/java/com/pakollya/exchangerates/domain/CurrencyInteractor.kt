package com.pakollya.exchangerates.domain

import com.pakollya.exchangerates.data.repository.CurrencyRepository
import com.pakollya.exchangerates.presentation.list.SortedByType
import com.pakollya.exchangerates.utils.toCurrencyList
import com.pakollya.exchangerates.utils.toCurrencyNameList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyInteractor @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend fun updateCurrencyListByName(name: String? = null) {
        val rateApiResponse = if (name.isNullOrEmpty()) repository.getRateApiResponse() else repository.getRateApiResponse(name)

        rateApiResponse?.let { response ->
            repository.insertCurrencyList(response.toCurrencyList())
        }
    }

    fun currencyListSortedBy(sortedByType: SortedByType?, isFavorite: Boolean = false) =
        repository.currencyListSortedBy(sortedByType, isFavorite)

    fun currencyNameList() = repository.currencyNameList()

    suspend fun updateCurrencyNameList() {
        repository.getSymbolApiResponse()?.let { response ->
            repository.insertCurrencyNameList(response.toCurrencyNameList())
        }
    }

    fun checkCurrencySettings() {
        if (!repository.hasCurrencySettings()) {
            repository.setDefaultSettings()
        }
    }

    fun getSortedByType() = repository.getSortedByType()

    fun updateSortedByType(sortedByType: SortedByType?) = repository.updateSortedByType(sortedByType)

    fun getBaseName(): Flow<String?> = repository.getBaseName()

    fun updateBaseName(baseName: String?) = repository.updateBaseName(baseName)

    fun hasCurrencyFavoriteByName(name: String): Boolean {
        return repository.hasCurrencyFavoriteByName(name)
    }

    fun setFavoriteByName(name: String?): Boolean? {
        name?.let {
            return if(repository.hasCurrencyFavoriteByName(name)) {
                repository.deleteCurrencyFavoriteByName(name)
                false
            } else {
                repository.insertCurrency(name)
                true
            }
        }
        return null
    }

    fun insertFavoriteByName(name: String?) {
        name?.let {
            repository.insertCurrency(it)
        }
    }
}
