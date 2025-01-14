package com.example.bin_info.info.domain.model

data class Info(
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