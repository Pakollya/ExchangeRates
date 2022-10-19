package com.pakollya.exchangerates.currencies.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.base.presentation.BaseViewModel
import com.pakollya.exchangerates.base.presentation.Init
import com.pakollya.exchangerates.base.presentation.Visibility
import com.pakollya.exchangerates.currencies.domain.CurrenciesInteractor
import com.pakollya.exchangerates.di.Base
import javax.inject.Inject

abstract class CurrenciesViewModelAbstract (
    private val currenciesInteractor: CurrenciesInteractor,
    private val communication: CurrenciesCommunications,
    dispatchers: Dispatchers,
) : BaseViewModel(dispatchers), Init, ObserveCurrencies, ShowCurrencies {

    private val atFinish = { communication.showProgress(Visibility.Gone()) }

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun)
            communication.showProgress(Visibility.Visible())
            showCurrencies()
    }

    override fun showCurrencies() {
        handle {
            currenciesInteractor.currencies(atFinish) {
                communication.showCurrencies(it)
            }
        }
    }

    override fun observeList(owner: LifecycleOwner, observer: Observer<CurrenсiesUi>) =
        communication.observeList(owner, observer)

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Visibility>) =
        communication.observeProgress(owner, observer)

    override fun observeBaseCurrency(owner: LifecycleOwner, observer: Observer<String>) =
        communication.observeBaseCurrency(owner, observer)

    override fun observeSorting(owner: LifecycleOwner, observer: Observer<Boolean>) =
        communication.observeSorting(owner, observer)

    override fun observeError(owner: LifecycleOwner, observer: Observer<String>) =
        communication.observeError(owner, observer)
}

interface ShowCurrencies {
    fun showCurrencies()
}

class CurrenciesViewModel @Inject constructor(
    @Base
    private val currenciesInteractor: CurrenciesInteractor,
    @Base
    private val communication: CurrenciesCommunications,
    dispatchers: Dispatchers,
) : CurrenciesViewModelAbstract(
    currenciesInteractor,
    communication,
    dispatchers
)