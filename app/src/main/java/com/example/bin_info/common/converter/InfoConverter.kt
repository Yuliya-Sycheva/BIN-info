package com.example.bin_info.common.converter

import android.content.Context
import com.example.bin_info.history.data.entity.HistoryEntity
import com.example.bin_info.info.data.dto.BINInfoResponse
import com.example.bin_info.info.domain.model.Info

class InfoConverter(context: Context) {
    fun convert(info: Info, bin: String): HistoryEntity = HistoryEntity(
        bin = bin,
        scheme = info.scheme,
        type = info.type,
        brand = info.brand,
        prepaid = info.prepaid,
        countryName = info.countryName,
        countryLatitude = info.countryLatitude,
        countryLongitude = info.countryLongitude,
        bankName = info.bankName,
        bankUrl = info.bankUrl,
        bankPhone = info.bankPhone,
        bankCity = info.bankCity
    )

    fun convert(binInfoResponse: BINInfoResponse): Info = Info(
        scheme = binInfoResponse.scheme ?: "-",
        type = binInfoResponse.type ?: "-",
        brand = binInfoResponse.brand ?: "-",
        prepaid = binInfoResponse.prepaid,
        countryName = binInfoResponse.country?.name ?: "-",
        countryLatitude = binInfoResponse.country?.latitude?.toString(),
        countryLongitude = binInfoResponse.country?.longitude?.toString(),
        bankName = binInfoResponse.bank?.name ?: "-",
        bankUrl = binInfoResponse.bank?.url,
        bankPhone = binInfoResponse.bank?.phone,
        bankCity = binInfoResponse.bank?.city ?: "-"
    )
}