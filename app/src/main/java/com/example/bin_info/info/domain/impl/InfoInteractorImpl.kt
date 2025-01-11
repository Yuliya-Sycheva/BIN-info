package com.example.bin_info.info.domain.impl

import com.example.bin_info.info.domain.api.InfoInteractor
import com.example.bin_info.info.domain.api.InfoRepository
import com.example.bin_info.info.domain.model.ErrorType
import com.example.bin_info.info.domain.model.Info
import com.example.bin_info.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InfoInteractorImpl(private val repository: InfoRepository) : InfoInteractor {
    override fun searchInfo(number: String): Flow<Pair<Info?, ErrorType?>> {
        return repository.searchInfo(number).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> Pair(null, result.errorType)
            }
        }
    }
}