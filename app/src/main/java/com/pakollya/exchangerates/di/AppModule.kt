package com.pakollya.exchangerates.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pakollya.exchangerates.data.api.Api
import com.pakollya.exchangerates.data.api.RateApiService
import com.pakollya.exchangerates.data.database.CacheDataSource.BaseDataSource
import com.pakollya.exchangerates.presentation.list.CurrencyListViewModel
import com.pakollya.exchangerates.presentation.base.ViewModelFactory
import com.pakollya.exchangerates.presentation.base.ViewModelKey
import com.pakollya.exchangerates.presentation.name.CurrencyNameListViewModel
import com.pakollya.exchangerates.utils.API_KEY
import com.pakollya.exchangerates.utils.BASE_URL
import com.pakollya.exchangerates.utils.DATABASE_NAME
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun provideRateApiService(api: Api): RateApiService = RateApiService(api, API_KEY)
}

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context) = BaseDataSource(context.applicationContext, DATABASE_NAME)
}

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyListViewModel::class)
    internal abstract fun bindCurrencyListViewModel(viewModel: CurrencyListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyNameListViewModel::class)
    internal abstract fun bindCurrencyNameListViewModel(viewModel: CurrencyNameListViewModel): ViewModel
}