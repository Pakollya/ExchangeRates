package com.pakollya.exchangerates.currencies.presentation

import com.pakollya.exchangerates.base.presentation.BaseView
import com.pakollya.exchangerates.base.presentation.ItemUi
import com.pakollya.exchangerates.utils.EUR

class BaseCurrencyUi(private val text: String = EUR): ItemUi {

    override fun type(): Int = 3

    override fun show(vararg views: BaseView) = views[0].show(text)

    override fun id(): String = text

    override fun content(): String = text
}