package com.example.bin_info.di

import com.example.bin_info.external.domain.ExternalInteractor
import com.example.bin_info.external.domain.impl.ExternalInteractorImpl
import com.example.bin_info.history.domain.HistoryInteractor
import com.example.bin_info.history.domain.impl.HistoryInteractorImpl
import com.example.bin_info.info.domain.api.InfoInteractor
import com.example.bin_info.info.domain.impl.InfoInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    factory<InfoInteractor> { InfoInteractorImpl(repository = get()) }
    factory<HistoryInteractor> { HistoryInteractorImpl(historyRepository = get()) }
    factory<ExternalInteractor> { ExternalInteractorImpl(externalRepository = get()) }
}