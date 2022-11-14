package com.pakollya.exchangerates.sorting.presentation

import com.pakollya.exchangerates.base.presentation.BaseView
import com.pakollya.exchangerates.base.presentation.ItemUi

data class SortingUi(
    private val id: String,
    private val text: String,
    private val isSorting: Boolean,
    private val changeSort: ChangeSort
) : ItemUi {

    override fun type(): Int = 5

    override fun show(vararg views: BaseView) {
        views[0].show(text)
        views[1].select(isSorting)
        views[2].apply {
            select(isSorting)
            handleClick{
                changeSort.changeSort(id)
            }
        }
    }

    override fun id(): String = id

    override fun content(): String = text + isSorting
}