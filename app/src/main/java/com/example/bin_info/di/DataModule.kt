package com.example.bin_info.di

import com.example.bin_info.info.data.network.BINlistAPI
import com.example.bin_info.info.data.network.NetworkClient
import com.example.bin_info.info.data.network.RetrofitNetworkClient
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://lookup.binlist.net/"

val dataModule = module {

    // Network
    single<NetworkClient> {
        RetrofitNetworkClient(api = get(), context = androidContext(), ioDispatcher = get(named("ioDispatcher")))
    }

    single<BINlistAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BINlistAPI::class.java)
    }

    single {
        Gson()
    }

    // Converters
    single<CoroutineDispatcher>(named("ioDispatcher")) {
        Dispatchers.IO
    }
}