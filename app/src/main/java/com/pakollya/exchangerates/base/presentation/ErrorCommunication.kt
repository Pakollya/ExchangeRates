package com.pakollya.exchangerates.base.presentation

interface ErrorCommunication {

    interface Observe : Communication.Observe<String>

    interface Update : Communication.Update<String>

    interface Mutable : Communication.Mutable<String>, Observe, Update

    class Base : Communication.SinglePostUpdate<String>(), Mutable
}