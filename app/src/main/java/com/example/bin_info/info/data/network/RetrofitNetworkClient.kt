package com.example.bin_info.info.data.network

import android.content.Context
import android.util.Log
import com.example.bin_info.info.data.dto.BINInfoRequest
import com.example.bin_info.info.data.dto.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class RetrofitNetworkClient(
    private val api: BINlistAPI,
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher,
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        val response = Response()

        if (!context.isNetworkConnected) {
            response.resultCode = ResultCode.CONNECTION_PROBLEM
        } else if (dto !is BINInfoRequest) {
            response.resultCode = ResultCode.BAD_REQUEST
        } else {
            return withContext(ioDispatcher) {
                try {
                    val apiResponse = api.getBinInfo(dto.number)
                    Log.d("API_RESPONSE", "Raw Response: ${apiResponse.toString()}")
                    Log.d("API_RESPONSE", "Bank Info: ${apiResponse.bank}")
                    apiResponse.apply { resultCode = ResultCode.SUCCESS }
                } catch (ex: HttpException) {
                    Log.e(TAG, "HTTP error: ${ex.message()}", ex)
                    response.resultCode = handleHttpException(ex)
                    response
                } catch (ex: SocketTimeoutException) {
                    Log.e(TAG, "Socket timeout: ${ex.message}", ex)
                    response.resultCode = ResultCode.CONNECTION_PROBLEM
                    response
                } catch (ex: IOException) {
                    Log.e(TAG, "IO error: ${ex.message}", ex)
                    response.resultCode = ResultCode.CONNECTION_PROBLEM
                    response
                }
            }
        }

        return response
    }

    private fun handleHttpException(ex: HttpException): Int {
        return when (ex.code()) {
            ResultCode.NOTHING_FOUND,
            ResultCode.SERVER_ERROR,
            ResultCode.LIMIT_ERROR,
            -> ex.code()

            else -> ResultCode.UNKNOWN_ERROR
        }
    }

    companion object {
        private const val TAG = "RetrofitNetworkClient"
    }
}