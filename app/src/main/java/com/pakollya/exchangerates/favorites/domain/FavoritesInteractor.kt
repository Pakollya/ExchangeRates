package com.pakollya.exchangerates.favorites.domain

import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.base.data.HandleError
import com.pakollya.exchangerates.base.domain.Interactor
import com.pakollya.exchangerates.currencies.domain.CommonCurrencyRepository
import com.pakollya.exchangerates.currencies.domain.CurrenciesDomain
import com.pakollya.exchangerates.currencies.presentation.CurrenсiesUi

interface FavoritesInteractor {

    suspend fun favorites(
        atFinish: () -> Unit,
        successful: (CurrenсiesUi) -> Unit
    )

    class Base(
        private val domainMapper: CurrenciesDomain.Mapper<CurrenсiesUi>,
        private val currencyRepository: CommonCurrencyRepository,
        dispatchers: Dispatchers,
        handleError: HandleError
    ) : Interactor.Abstract(dispatchers, handleError), FavoritesInteractor {

        override suspend fun favorites(
            atFinish: () -> Unit,
            successful: (CurrenсiesUi) -> Unit
        ) = handle(successful, atFinish) {
            val data = currencyRepository.favorites()
            return@handle data.map(domainMapper)
        }
    }
}