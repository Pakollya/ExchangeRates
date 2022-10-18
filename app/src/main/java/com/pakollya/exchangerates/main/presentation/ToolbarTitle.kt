package com.pakollya.exchangerates.main.presentation

interface ToolbarTitle {
    fun setTitle(title: String?)

    class Empty : ToolbarTitle {
        override fun setTitle(title: String?) = Unit
    }
}