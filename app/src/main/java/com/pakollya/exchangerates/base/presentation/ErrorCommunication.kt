package com.pakollya.exchangerates.base.presentation

interface ErrorCommunication : Communication.Mutable<String> {
    class Base : Communication.SinglePostUpdate<String>(), ErrorCommunication
}