package com.example.bin_info.common.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bin_info.history.data.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryEntity)

    @Query("SELECT * FROM history ORDER BY bin DESC")
    fun getAllHistory(): Flow<List<HistoryEntity>>
}