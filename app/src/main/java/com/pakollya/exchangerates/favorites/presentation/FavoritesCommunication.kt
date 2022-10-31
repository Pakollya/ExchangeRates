package com.pakollya.exchangerates.favorites.presentation

import com.pakollya.exchangerates.base.presentation.Communication
import com.pakollya.exchangerates.currencies.presentation.CurrenсiesUi

interface FavoritesCommunication : Communication.Mutable<CurrenсiesUi> {
    class Base : Communication.UiUpdate<CurrenсiesUi>(), FavoritesCommunication
}