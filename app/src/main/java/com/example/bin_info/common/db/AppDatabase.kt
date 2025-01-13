package com.example.bin_info.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bin_info.history.data.entity.HistoryEntity

@Database(version = 2, entities = [HistoryEntity::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
}