package com.example.bin_info.info.data.dto

data class BINInfoResponse(
    val scheme: String?, // Тип карты (visa, mastercard)
    val type: String?, // Дебетовая/кредитная
    val brand: String?, // Бренд карты
    val prepaid: Boolean?, // Предоплаченная карта
    val country: CountryInfo?, // Информация о стране
    val bank: BankInfo? // Информация о банке
) : Response()
