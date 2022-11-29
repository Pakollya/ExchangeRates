package com.pakollya.exchangerates.base.presentation

interface BottomNavigationCommunication: Communication.Mutable<Int> {
    class Base : Communication.UiUpdate<Int>(), BottomNavigationCommunication
}