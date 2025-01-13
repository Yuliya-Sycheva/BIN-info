package com.example.bin_info.history.presentation

import com.example.bin_info.info.domain.model.Info

sealed interface HistoryState {
    data object Empty : HistoryState

    data object Error : HistoryState
    data class Content(
        val history: List<Info>
    ) : HistoryState
}