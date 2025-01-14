package com.example.bin_info.info.data.dto

data class BINInfoResponse(
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: CountryInfo?,
    val bank: BankInfo?
) : Response()
