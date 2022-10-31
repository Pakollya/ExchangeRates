package com.pakollya.exchangerates.names.presentation

import com.pakollya.exchangerates.base.core.Mapper
import com.pakollya.exchangerates.base.presentation.ItemUi

interface ItemsUi : Mapper.Unit<Mapper.Unit<List<ItemUi>>> {

    class Base(private val list: List<ItemUi>) : ItemsUi {
        override fun map(data: Mapper.Unit<List<ItemUi>>) = data.map(list)
    }
}