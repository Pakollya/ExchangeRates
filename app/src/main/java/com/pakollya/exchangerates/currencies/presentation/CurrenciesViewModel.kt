package com.pakollya.exchangerates.currencies.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.base.presentation.BaseViewModel
import com.pakollya.exchangerates.base.presentation.ErrorCommunication
import com.pakollya.exchangerates.base.presentation.ProgressCommunication
import com.pakollya.exchangerates.base.presentation.Visibility
import com.pakollya.exchangerates.currencies.domain.CurrenciesInteractor
import com.pakollya.exchangerates.names.data.UpdateBaseCurrency
import com.pakollya.exchangerates.sorting.data.UpdateSorting
import javax.inject.Inject

class CurrenciesViewModel @Inject constructor(
    private val currenciesInteractor: CurrenciesInteractor,
    private val progressCommunication: ProgressCommunication.Mutable,
    private val updateBaseCurrency: UpdateBaseCurrency.Mutable,
    private val updateSorting: UpdateSorting.Mutable,
    private val errorCommunication: ErrorCommunication.Mutable,
    currenciesCommunication: CurrenciesCommunication,
    dispatchers: Dispatchers
) : BaseViewModel<CurrenсiesUi>(currenciesCommunication, dispatchers) {

    private val atFinish = {
        progressCommunication.map(Visibility.Gone())
    }

    init {
        updateCurrencies()
    }

    fun updateCurrencies() {
        progressCommunication.map(Visibility.Visible())
        handle {
            currenciesInteractor.currencies(atFinish) {
                communication.map(it)
            }
        }
    }

    fun observeProgress(owner: LifecycleOwner, observer: Observer<Visibility>) {
        progressCommunication.observe(owner, observer)
    }

    fun observeBaseCurrency(owner: LifecycleOwner, observer: Observer<String>) {
        updateBaseCurrency.observe(owner, observer)
    }

    fun observeSorting(owner: LifecycleOwner, observer: Observer<Boolean>) {
        updateSorting.observe(owner, observer)
    }

    fun observeError(owner: LifecycleOwner, observer: Observer<String>) {
        errorCommunication.observe(owner, observer)
    }
}