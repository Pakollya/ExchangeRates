package com.pakollya.exchangerates.names.presentation

import com.pakollya.exchangerates.base.presentation.Communication

interface NamesListCommunication : Communication.Mutable<ItemsUi> {
    class Base : Communication.UiUpdate<ItemsUi>(), NamesListCommunication
}