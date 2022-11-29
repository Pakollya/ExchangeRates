package com.pakollya.exchangerates.base.presentation

interface ProgressCommunication: Communication.Mutable<Int> {
    class Base : Communication.UiUpdate<Int>(), ProgressCommunication
}