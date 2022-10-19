package com.pakollya.exchangerates.base.presentation

interface ProgressCommunication: Communication.Mutable<Visibility> {
    class Base : Communication.UiUpdate<Visibility>(), ProgressCommunication
}