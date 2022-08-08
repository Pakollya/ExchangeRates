package com.pakollya.exchangerates.presentation.name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pakollya.exchangerates.data.database.name.CurrencyName
import com.pakollya.exchangerates.domain.CurrencyInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrencyNameListViewModel@Inject constructor(
    private val currencyInteractor: CurrencyInteractor
) : ViewModel() {
    private val _names: StateFlow<List<CurrencyName>?> = currencyInteractor.currencyNameList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    val names: StateFlow<List<CurrencyName>?> = _names

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currencyInteractor.updateCurrencyNameList()
        }
    }

    fun saveBaseName(name: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyInteractor.updateBaseName(name)
        }
    }
}