package com.example.bin_info

import android.app.Application
import com.example.bin_info.di.dataModule
import com.example.bin_info.di.interactorModule
import com.example.bin_info.di.repositoryModule
import com.example.bin_info.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
            printLogger()
        }
    }
}