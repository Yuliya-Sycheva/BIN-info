package com.example.bin_info.common.converter

import com.example.bin_info.history.data.entity.HistoryEntity
import com.example.bin_info.info.domain.model.Info

class InfoDbConverter {
    fun convert(info: Info, bin: String): HistoryEntity = HistoryEntity(
        bin = bin,
        scheme = info.scheme,
        type = info.type,
        brand = info.brand,
        prepaid = if (info.prepaid == true) "Yes" else "No",
        countryName = info.countryName,
        countryLatitude = info.countryLatitude?.toString(),
        countryLongitude = info.countryLongitude?.toString(),
        bankName = info.bankName,
        bankUrl = info.bankUrl,
        bankPhone = info.bankPhone,
        bankCity = info.bankCity
    )
}