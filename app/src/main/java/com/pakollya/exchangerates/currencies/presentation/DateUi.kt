package com.pakollya.exchangerates.currencies.presentation

import com.pakollya.exchangerates.base.presentation.BaseView
import com.pakollya.exchangerates.base.presentation.ItemUi

class DateUi(private val text: String) : ItemUi {

    override fun type(): Int = 2

    override fun show(vararg views: BaseView) = views[0].show(text)

    override fun id(): String = text

    override fun content(): String = text
}