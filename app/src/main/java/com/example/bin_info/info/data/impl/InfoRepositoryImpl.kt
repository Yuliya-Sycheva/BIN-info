package com.example.bin_info.info.data.impl

import com.example.bin_info.info.data.dto.BINInfoRequest
import com.example.bin_info.info.data.dto.BINInfoResponse
import com.example.bin_info.info.data.network.NetworkClient
import com.example.bin_info.info.data.network.ResultCode
import com.example.bin_info.info.domain.api.InfoRepository
import com.example.bin_info.info.domain.model.ErrorType
import com.example.bin_info.info.domain.model.Info
import com.example.bin_info.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class InfoRepositoryImpl(
    private val networkClient: NetworkClient,
    private val ioDispatcher: CoroutineDispatcher,
) : InfoRepository {

    override fun searchInfo(number: String): Flow<Resource<Info>> = flow {
        val response = networkClient.doRequest(BINInfoRequest(number))
        when (response.resultCode) {
            ResultCode.SUCCESS -> {
                val binInfoResponse = response as BINInfoResponse
                if (binInfoResponse.isAllFieldsNull()) {
                    emit(Resource.Error(ErrorType.NOTHING_FOUND))
                } else {
                    emit(Resource.Success(binInfoResponse.toInfo()))
                }
            }

            ResultCode.CONNECTION_PROBLEM -> emit(Resource.Error(ErrorType.CONNECTION_PROBLEM))
            ResultCode.BAD_REQUEST -> emit(Resource.Error(ErrorType.BAD_REQUEST))
            ResultCode.NOTHING_FOUND -> emit(Resource.Error(ErrorType.NOTHING_FOUND))
            ResultCode.SERVER_ERROR -> emit(Resource.Error(ErrorType.SERVER_ERROR))
            ResultCode.LIMIT_ERROR -> emit(Resource.Error(ErrorType.LIMIT_ERROR))
            else -> emit(Resource.Error(ErrorType.UNKNOWN_ERROR))
        }
    }.flowOn(ioDispatcher)
}

fun BINInfoResponse.isAllFieldsNull(): Boolean {
    return scheme == null &&
            type == null &&
            brand == null &&
            prepaid == null &&
            country == null &&
            bank == null
}

fun BINInfoResponse.toInfo(): Info {
    return Info(
        scheme = scheme,
        type = type,
        brand = brand,
        prepaid = prepaid,
        countryName = country?.name,
        countryLongitude = country?.longitude,
        countryLatitude = country?.latitude,
        bankName = bank?.name,
        bankUrl = bank?.url,
        bankPhone = bank?.phone,
        bankCity = bank?.city,
    )
}