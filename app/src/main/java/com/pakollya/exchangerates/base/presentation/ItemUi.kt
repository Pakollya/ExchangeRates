package com.pakollya.exchangerates.base.presentation

interface ItemUi {

    fun type(): Int

    fun show(vararg views: BaseView)

    fun id(): String

    fun content(): String
}