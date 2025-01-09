package com.example.bin_info.di

import com.example.bin_info.info.domain.api.InfoInteractor
import com.example.bin_info.info.domain.impl.InfoInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    factory<InfoInteractor> { InfoInteractorImpl(repository = get()) }
}