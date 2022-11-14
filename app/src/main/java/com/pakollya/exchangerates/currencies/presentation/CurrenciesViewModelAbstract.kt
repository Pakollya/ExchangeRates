package com.pakollya.exchangerates.currencies.presentation

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.base.presentation.BaseViewModel
import com.pakollya.exchangerates.base.presentation.Init
import com.pakollya.exchangerates.currencies.domain.CurrenciesInteractor

abstract class CurrenciesViewModelAbstract(
    private val currenciesInteractor: CurrenciesInteractor,
    private val communication: CurrenciesCommunications,
    dispatchers: Dispatchers,
) : BaseViewModel(dispatchers), Init, ObserveCurrencies, ShowCurrencies {

    override fun init(isFirstRun: Boolean) {
        communication.showNavigation(VISIBLE)
        if (isFirstRun) {
            communication.showProgress(VISIBLE)
            showCurrencies()
        }
    }

    override fun showCurrencies() {
        handle {
            currenciesInteractor.currencies {
                communication.showCurrencies(it)
                communication.showProgress(GONE)
            }
        }
    }

    override fun observeList(owner: LifecycleOwner, observer: Observer<CurrenсiesUi>) =
        communication.observeList(owner, observer)

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) =
        communication.observeProgress(owner, observer)

    override fun observeSorting(owner: LifecycleOwner, observer: Observer<Boolean>) =
        communication.observeSorting(owner, observer)

    override fun observeError(owner: LifecycleOwner, observer: Observer<String>) =
        communication.observeError(owner, observer)
}