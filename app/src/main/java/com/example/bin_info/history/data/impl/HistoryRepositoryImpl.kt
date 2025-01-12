package com.example.bin_info.history.data.impl

import com.example.bin_info.common.converter.InfoDbConverter
import com.example.bin_info.common.db.AppDatabase
import com.example.bin_info.history.domain.HistoryRepository
import com.example.bin_info.info.domain.model.Info
import kotlinx.coroutines.flow.Flow

class HistoryRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val infoDbConverter: InfoDbConverter
) : HistoryRepository {
    override suspend fun addInfoToHistory(info: Info, bin: String) {
        appDatabase.historyDao().insertHistory(infoDbConverter.convert(info, bin))
    }

    override fun getHistory(): Flow<List<Info>> {
        TODO("Not yet implemented")
    }

}