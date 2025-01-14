package com.example.bin_info.common.util

import com.example.bin_info.info.domain.model.ErrorType

sealed class Resource<T>(val data: T? = null, val errorType: ErrorType? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(errorType: ErrorType) : Resource<T>(null, errorType)
}