package com.example.bin_info.history.domain.impl

import com.example.bin_info.history.domain.HistoryInteractor
import com.example.bin_info.history.domain.HistoryRepository
import com.example.bin_info.info.domain.model.Info
import kotlinx.coroutines.flow.Flow

class HistoryInteractorImpl(val historyRepository: HistoryRepository) : HistoryInteractor {
    override suspend fun addInfoToHistory(info: Info, bin: String) {
        historyRepository.addInfoToHistory(info, bin)
    }

    override fun getHistory(): Flow<List<Info>> {
        return historyRepository.getHistory()
    }
}