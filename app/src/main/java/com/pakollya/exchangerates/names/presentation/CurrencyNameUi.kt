package com.pakollya.exchangerates.names.presentation

import com.pakollya.exchangerates.base.presentation.BaseView
import com.pakollya.exchangerates.base.presentation.ItemUi

class CurrencyNameUi(
    private val id: String,
    private val name: String,
    private val fullName: String,
    private val isBase: Boolean,
    private val changeBase: ChangeBaseCurrency
) : ItemUi {

    override fun type(): Int = 4

    override fun show(vararg views: BaseView) {
        views[0].show(name)
        views[1].show(fullName)
        views[2].select(isBase)
        views[3].handleClick {
            changeBase.changeBase(id)
        }
    }

    override fun id(): String = id

    override fun content(): String = name + isBase
}