package com.pakollya.exchangerates.names.data.cache

import com.pakollya.exchangerates.names.data.cloud.CurrencyNameCloud
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface CurrencyNamesCacheDataSource {

    suspend fun names(): CurrencyNameCloud.CurrencyNames

    suspend fun saveNames(names: CurrencyNameCloud.CurrencyNames)

    class Base(
        private val dao: CurrencyNameDao,
    ) : CurrencyNamesCacheDataSource {

        private val mutex = Mutex()

        override suspend fun names(): CurrencyNameCloud.CurrencyNames = mutex.withLock {
            val count = dao.namesCount()
            return if (count == null || count < 1)
                CurrencyNameCloud.CurrencyNames(emptyMap())
            else
                dao.names()
        }

        override suspend fun saveNames(names: CurrencyNameCloud.CurrencyNames) = mutex.withLock {
            dao.insertNames(names)
        }
    }
}