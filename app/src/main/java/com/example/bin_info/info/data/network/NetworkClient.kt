package com.example.bin_info.info.data.network

import com.example.bin_info.info.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}