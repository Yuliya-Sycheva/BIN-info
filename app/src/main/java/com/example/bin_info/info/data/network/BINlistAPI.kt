package com.example.bin_info.info.data.network

import com.example.bin_info.info.data.dto.BINInfoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BINlistAPI {
    @GET("/{bin}")
    suspend fun getBinInfo(@Path("bin") bin: String): BINInfoResponse
}