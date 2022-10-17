package com.pakollya.exchangerates.currencies.presentation

import com.pakollya.exchangerates.base.core.Mapper
import com.pakollya.exchangerates.base.presentation.ItemUi

interface CurrenсiesUi : Mapper.Unit<Mapper.Unit<List<ItemUi>>> {

    fun mapBase(data: Mapper.Unit<ItemUi>)

    class Base(private val list: List<ItemUi>, private val base: ItemUi) : CurrenсiesUi {
        override fun map(data: Mapper.Unit<List<ItemUi>>) = data.map(list)

        override fun mapBase(data: Mapper.Unit<ItemUi>) = data.map(base)
    }
}