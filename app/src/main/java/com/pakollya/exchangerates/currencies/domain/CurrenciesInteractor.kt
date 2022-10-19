package com.pakollya.exchangerates.currencies.domain

import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.base.data.HandleError
import com.pakollya.exchangerates.base.domain.Interactor
import com.pakollya.exchangerates.currencies.presentation.CurrenсiesUi

interface CurrenciesInteractor {

    suspend fun currencies(
        atFinish: () -> Unit,
        successful: (CurrenсiesUi) -> Unit
    )

    class Base(
        private val domainMapper: CurrenciesDomain.Mapper<CurrenсiesUi>,
        private val currencyRepository: CommonCurrencyRepository,
        dispatchers: Dispatchers,
        handleError: HandleError
    ) : Interactor.Abstract(dispatchers, handleError), CurrenciesInteractor {

        override suspend fun currencies(
            atFinish: () -> Unit,
            successful: (CurrenсiesUi) -> Unit,
        ) = handle(successful, atFinish) {
            val data = currencyRepository.currencies()
            return@handle data.map(domainMapper)
        }
    }

    class Favorite(
        private val domainMapper: CurrenciesDomain.Mapper<CurrenсiesUi>,
        private val currencyRepository: CommonCurrencyRepository,
        dispatchers: Dispatchers,
        handleError: HandleError
    ) : Interactor.Abstract(dispatchers, handleError), CurrenciesInteractor {

        override suspend fun currencies(
            atFinish: () -> Unit,
            successful: (CurrenсiesUi) -> Unit
        ) = handle(successful, atFinish) {
            val data = currencyRepository.favorites()
            return@handle data.map(domainMapper)
        }
    }
}