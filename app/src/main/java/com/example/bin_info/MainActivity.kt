package com.example.bin_info

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.bin_info.databinding.ActivityMainBinding
import com.example.bin_info.info.data.dto.BINInfoRequest
import com.example.bin_info.info.data.dto.BINInfoResponse
import com.example.bin_info.info.data.network.BINlistAPI
import com.example.bin_info.info.data.network.RetrofitNetworkClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val api = createApi()

        val networkClient = RetrofitNetworkClient(api, this, Dispatchers.IO)

        checkNetworkRequest(networkClient)
    }

    private fun createApi(): BINlistAPI {
        val okHttpClient = OkHttpClient.Builder().build()

        return Retrofit.Builder()
            .baseUrl("https://lookup.binlist.net/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BINlistAPI::class.java)
    }

    private fun checkNetworkRequest(networkClient: RetrofitNetworkClient) {
        val testBin = "45717360"

        CoroutineScope(Dispatchers.Main).launch {
            val response = networkClient.doRequest(BINInfoRequest(testBin))

            Log.i("MainActivity", "Result Code: ${response.resultCode}")
            if (response.resultCode == 200 && response is BINInfoResponse) {
                // Успешный запрос, выводим данные в лог
                Log.i("MainActivity", "Request successful! Response: ${response}")
            } else {
                Log.e("MainActivity", "Request failed with code: ${response.resultCode}")
            }
        }
    }
}