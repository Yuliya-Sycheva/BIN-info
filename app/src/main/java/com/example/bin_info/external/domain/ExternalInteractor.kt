package com.example.bin_info.external.domain

interface ExternalInteractor {
    fun openMap(latitude: String, longitude: String)
    fun openPhone(phone: String)
    fun openUrl(url: String)
}