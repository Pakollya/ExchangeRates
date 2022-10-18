package com.pakollya.exchangerates.currencies.presentation

import com.pakollya.exchangerates.base.presentation.Communication

interface CurrenciesCommunication : Communication.Mutable<CurrenсiesUi> {
    class Base : Communication.UiUpdate<CurrenсiesUi>(), CurrenciesCommunication
}