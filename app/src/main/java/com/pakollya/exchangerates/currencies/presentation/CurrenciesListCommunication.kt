package com.pakollya.exchangerates.currencies.presentation

import com.pakollya.exchangerates.base.presentation.Communication

interface CurrenciesListCommunication : Communication.Mutable<CurrenсiesUi> {
    class Base : Communication.UiUpdate<CurrenсiesUi>(), CurrenciesListCommunication
}