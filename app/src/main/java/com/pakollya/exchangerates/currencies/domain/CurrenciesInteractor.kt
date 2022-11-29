package com.pakollya.exchangerates.currencies.domain

import com.pakollya.exchangerates.base.core.Dispatchers
import com.pakollya.exchangerates.base.data.HandleError
import com.pakollya.exchangerates.base.domain.Interactor
import com.pakollya.exchangerates.currencies.presentation.CurrenсiesUi

interface CurrenciesInteractor {

    suspend fun currencies(
        successful: (CurrenсiesUi) -> Unit
    )

    class Base(
        private val mapper: CurrenciesDomain.Mapper<CurrenсiesUi>,
        private val repository: CurrenciesRepository,
        dispatchers: Dispatchers,
        handleError: HandleError
    ) : Interactor.Abstract(dispatchers, handleError), CurrenciesInteractor {

        override suspend fun currencies(
            successful: (CurrenсiesUi) -> Unit
        ) = handle(successful) {
            val data = repository.currencies()
            return@handle data.map(mapper)
        }
    }
}