package com.example.bin_info.info.domain.api

import com.example.bin_info.info.domain.model.ErrorType
import com.example.bin_info.info.domain.model.Info
import kotlinx.coroutines.flow.Flow

interface InfoInteractor {
    fun searchInfo(number: String): Flow<Pair<Info?, ErrorType?>>
}