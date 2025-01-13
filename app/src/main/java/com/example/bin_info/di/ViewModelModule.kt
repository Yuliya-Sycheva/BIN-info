package com.example.bin_info.di

import com.example.bin_info.history.presentation.HistoryViewModel
import com.example.bin_info.info.presentation.InfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        InfoViewModel(infoInteractor = get(), historyInteractor = get())
    }

    viewModel {
        HistoryViewModel(historyInteractor = get())
    }
}