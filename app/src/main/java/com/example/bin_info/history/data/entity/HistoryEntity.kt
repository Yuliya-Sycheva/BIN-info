package com.example.bin_info.history.data.entity

import androidx.room.Entity

@Entity(tableName = "history")
data class HistoryEntity(
    val bin: String,
    val scheme: String?, // Тип карты (visa, mastercard)
    val type: String?, // Дебетовая/кредитная
    val brand: String?, // Бренд карты
    val prepaid: String?, // Предоплаченная карта
    val countryName: String?, // Название страны
    val countryLatitude: String?, // Широта
    val countryLongitude: String?, // Долгота
    val bankName: String?, // Название банка
    val bankUrl: String?, // Сайт
    val bankPhone: String?, // Телефон
    val bankCity: String? // Город
)
