package com.pakollya.exchangerates.sorting.data

import com.pakollya.exchangerates.base.presentation.Communication

interface UpdateSorting: Communication.Mutable<Boolean> {
    class Base : Communication.UiUpdate<Boolean>(), UpdateSorting
}