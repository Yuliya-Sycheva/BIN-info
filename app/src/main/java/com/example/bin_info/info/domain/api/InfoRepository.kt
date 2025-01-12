package com.example.bin_info.info.domain.api

import com.example.bin_info.info.domain.model.Info
import com.example.bin_info.common.util.Resource
import kotlinx.coroutines.flow.Flow

interface InfoRepository {
    fun searchInfo(number: String): Flow<Resource<Info>>
}