package com.pakollya.exchangerates.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pakollya.exchangerates.data.database.currency.Currency
import com.pakollya.exchangerates.domain.CurrencyInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SortedByType(val id: Int) {
    SORTED_BY_NAME(0),
    SORTED_BY_NAME_DESC(1),
    SORTED_BY_VALUE(2),
    SORTED_BY_VALUE_DESC(3)
}

class CurrencyListViewModel @Inject constructor(
    private val currencyInteractor: CurrencyInteractor
) : ViewModel() {
    private val _sortedByType: StateFlow<SortedByType?> = currencyInteractor.getSortedByType()
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = null
        )
    val sortedByType: StateFlow<SortedByType?> = _sortedByType

    private val _currencies: MutableStateFlow<List<Currency>?> = MutableStateFlow(null)
    val currencies: StateFlow<List<Currency>?> = _currencies

    private var _baseName: StateFlow<String?> = currencyInteractor.getBaseName()
        .stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = null
    )
    val baseName: StateFlow<String?> = _baseName

    var isFavoriteList = false

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currencyInteractor.checkCurrencySettings()
            currencyInteractor.updateCurrencyNameList()
            currencyInteractor.updateCurrencyListByName(baseName.value)
            currencyInteractor.getSortedByType().collect{
                _currencies.value = currencyInteractor.currencyListSortedBy(it, isFavoriteList)
            }
        }
    }

    private fun updateCurrencies() {
        _currencies.value = currencyInteractor.currencyListSortedBy(sortedByType.value, isFavoriteList)
    }

    fun updateCurrencyListByName() {
        viewModelScope.launch(Dispatchers.IO) {
            currencyInteractor.updateCurrencyListByName(baseName.value)
            updateCurrencies()
        }
    }

    fun onSortClick(type: SortedByType) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyInteractor.updateSortedByType(
                if (sortedByType.value == type) null else type
            )
        }
    }

    fun onFavoriteClick() {
        if (isFavoriteList) {
            viewModelScope.launch(Dispatchers.IO) {
                updateCurrencies()
            }
        }
    }

    fun recoverFavorite(name: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyInteractor.insertFavoriteByName(name)
            updateCurrencies()
        }
    }
}