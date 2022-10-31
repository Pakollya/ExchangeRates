package com.pakollya.exchangerates.base.presentation

interface BottomNavigationCommunication: Communication.Mutable<Visibility> {
    class Base : Communication.UiUpdate<Visibility>(), BottomNavigationCommunication
}