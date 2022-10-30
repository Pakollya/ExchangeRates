package com.pakollya.exchangerates.sorting.presentation

import com.pakollya.exchangerates.base.presentation.Communication
import com.pakollya.exchangerates.names.presentation.ItemsUi

interface SortingListCommunication : Communication.Mutable<ItemsUi> {
    class Base : Communication.UiUpdate<ItemsUi>(), SortingListCommunication
}