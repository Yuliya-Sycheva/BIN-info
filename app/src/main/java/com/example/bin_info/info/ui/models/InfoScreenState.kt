package com.example.bin_info.info.ui.models

import com.example.bin_info.info.domain.model.Info

sealed interface InfoScreenState {
    data object Default : InfoScreenState
    data object Loading : InfoScreenState
    data class Content(val info: Info) : InfoScreenState
    data object InternetError : InfoScreenState
    data object ServerError : InfoScreenState
    data object NothingFound : InfoScreenState
    data object LimitError : InfoScreenState
}