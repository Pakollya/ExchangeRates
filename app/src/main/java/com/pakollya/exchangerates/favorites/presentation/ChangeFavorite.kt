package com.pakollya.exchangerates.favorites.presentation

import com.pakollya.exchangerates.sorting.data.UpdateSorting

interface ChangeFavorite {

    fun changeFavorite(id: String)

    class Base(
        private val changeFavorite: ChangeFavorite,
        private val communication: UpdateSorting.Update
    ) : ChangeFavorite {

        override fun changeFavorite(id: String) {
            changeFavorite.changeFavorite(id)
            communication.map(true)
        }
    }
}