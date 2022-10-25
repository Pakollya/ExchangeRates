package com.pakollya.exchangerates.names.data

import com.pakollya.exchangerates.base.presentation.Communication

interface BaseCurrencyCommunication: Communication.Mutable<String> {
    class Base : Communication.UiUpdate<String>(), BaseCurrencyCommunication
}