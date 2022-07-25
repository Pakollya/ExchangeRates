package com.pakollya.exchangerates.di

import android.content.Context
import com.pakollya.exchangerates.data.CurrencyRepository
import com.pakollya.exchangerates.data.api.RateApiService
import com.pakollya.exchangerates.data.database.CacheDataSource.BaseDataSource
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApiModule::class, DatabaseModule::class])
@Singleton
interface AppComponent {
    val rateApiService: RateApiService
    val appDatabase: BaseDataSource
    val currencyRepository: CurrencyRepository

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}