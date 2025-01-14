package com.example.bin_info.history.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = false)
    val bin: String,
    val scheme: String,
    val type: String,
    val brand: String,
    val prepaid: Boolean?,
    val countryName: String,
    val countryLatitude: String?,
    val countryLongitude: String?,
    val bankName: String,
    val bankUrl: String?,
    val bankPhone: String?,
    val bankCity: String
)
