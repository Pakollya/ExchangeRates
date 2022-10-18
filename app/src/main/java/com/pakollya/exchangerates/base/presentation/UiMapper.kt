package com.pakollya.exchangerates.base.presentation

import com.pakollya.exchangerates.base.core.Mapper

abstract class UiMapperAbstract<T : ItemUi>(
    private val view: BaseView
): Mapper.Unit<T>{

    override fun map(data: T){
        data.show(view)
    }
}

class UiMapper(view: BaseView): UiMapperAbstract<ItemUi>(view)