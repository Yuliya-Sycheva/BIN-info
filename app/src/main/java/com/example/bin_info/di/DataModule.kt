package com.example.bin_info.di

import androidx.room.Room
import com.example.bin_info.common.converter.InfoConverter
import com.example.bin_info.common.db.AppDatabase
import com.example.bin_info.info.data.network.BINlistAPI
import com.example.bin_info.info.data.network.NetworkClient
import com.example.bin_info.info.data.network.RetrofitNetworkClient
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
        RetrofitNetworkClient(
            api = get(),
            context = androidContext(),
            ioDispatcher = get(named("ioDispatcher"))
        )
    }

    single<BINlistAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BINlistAPI::class.java)
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    // Dispatchers
    single<CoroutineDispatcher>(named("ioDispatcher")) {
        Dispatchers.IO
    }

    // Converters
    factory { InfoConverter() }
}