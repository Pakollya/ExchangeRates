package com.pakollya.exchangerates.favorites.presentation

import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.currencies.domain.CurrenciesInteractor
import com.pakollya.exchangerates.currencies.presentation.CurrenciesCommunications
import com.pakollya.exchangerates.currencies.presentation.CurrenciesViewModelAbstract
import com.pakollya.exchangerates.di.Favorite
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    @Favorite
    private val currenciesInteractor: CurrenciesInteractor,
    @Favorite
    private val communication: CurrenciesCommunications,
    dispatchers: Dispatchers,
) : CurrenciesViewModelAbstract(
    currenciesInteractor,
    communication,
    dispatchers
)