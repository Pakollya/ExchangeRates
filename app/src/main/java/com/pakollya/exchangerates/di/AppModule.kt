package com.pakollya.exchangerates.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pakollya.exchangerates.data.api.Api
import com.pakollya.exchangerates.data.api.RateApiService
import com.pakollya.exchangerates.data.database.CacheDataSource
import com.pakollya.exchangerates.data.database.CacheDataSource.BaseDataSource
import com.pakollya.exchangerates.utils.API_KEY
import com.pakollya.exchangerates.utils.BASE_URL
import com.pakollya.exchangerates.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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