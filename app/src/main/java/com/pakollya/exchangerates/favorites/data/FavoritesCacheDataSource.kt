package com.pakollya.exchangerates.favorites.data

import com.pakollya.exchangerates.favorites.presentation.ChangeFavorite

interface FavoritesCacheDataSource : ChangeFavorite, IsFavorite {

    class Base(
        private val favorites: FavoriteCurrencies.Mutable
    ) : FavoritesCacheDataSource {

        override fun changeFavorite(id: String) {
            val data = favorites.read().toMutableSet()
            if (isFavorite(id))
                data.remove(id)
            else
                data.add(id)
            favorites.save(data)
        }

        override fun isFavorite(id: String) = favorites.read().contains(id)
    }
}