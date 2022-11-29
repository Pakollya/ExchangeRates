package com.pakollya.exchangerates.currencies.presentation

import com.pakollya.exchangerates.base.presentation.BaseView
import com.pakollya.exchangerates.base.presentation.ItemUi
import com.pakollya.exchangerates.favorites.presentation.ChangeFavorite

data class CurrencyUi(
    private val id: String,
    private val name: String,
    private val value: String,
    private val isFavorite: Boolean,
    private val changeFavorite: ChangeFavorite
) : ItemUi {

    override fun type(): Int = 1

    override fun show(vararg views: BaseView) {
        views[0].show(name)
        views[1].show(value)
        views[2].select(isFavorite)
        views[3].handleClick {
            changeFavorite.changeFavorite(id)
        }
    }

    override fun id(): String = id

    override fun content(): String = value + isFavorite
}