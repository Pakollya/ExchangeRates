package com.pakollya.exchangerates.main.presentation

interface ToolbarNavigation {
    fun setDirection(direction: Int? = null)

    class Empty : ToolbarNavigation {
        override fun setDirection(direction: Int?) = Unit
    }
}