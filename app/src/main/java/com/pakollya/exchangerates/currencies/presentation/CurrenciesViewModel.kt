package com.pakollya.exchangerates.currencies.presentation

import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.currencies.domain.CurrenciesInteractor
import com.pakollya.exchangerates.di.Base
import javax.inject.Inject

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