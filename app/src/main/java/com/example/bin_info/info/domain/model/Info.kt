package com.example.bin_info.info.domain.model

data class Info(
    val bin: String,
    val scheme: String, // Тип карты (visa, mastercard)
    val type: String, // Дебетовая/кредитная
    val brand: String, // Бренд карты
    val prepaid: Boolean?, // Предоплаченная карта
    val countryName: String, // Название страны
    val countryLatitude: String?, // Широта
    val countryLongitude: String?, // Долгота
    val bankName: String, // Название банка
    val bankUrl: String?, // Сайт
    val bankPhone: String?, // Телефон
    val bankCity: String // Город
)