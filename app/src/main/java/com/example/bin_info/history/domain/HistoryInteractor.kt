package com.example.bin_info.history.domain

import com.example.bin_info.info.domain.model.Info
import kotlinx.coroutines.flow.Flow

interface HistoryInteractor {
    suspend fun addInfoToHistory(info: Info, bin: String)
    fun getHistory(): Flow<List<Info>>
}