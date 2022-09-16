package com.pakollya.exchangerates.base.presentation

interface ProgressCommunication {

    interface Update : Communication.Update<Visibility>

    interface Observe : Communication.Observe<Visibility>

    interface Mutable : Update, Observe

    class Base : Communication.UiUpdate<Visibility>(), Mutable
}