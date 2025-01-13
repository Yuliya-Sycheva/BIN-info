package com.example.bin_info.info.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bin_info.R
import com.example.bin_info.info.domain.api.InfoInteractor
import com.example.bin_info.info.domain.model.ErrorType
import com.example.bin_info.info.domain.model.Info
import com.example.bin_info.info.ui.models.InfoScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InfoViewModel(
    private val infoInteractor: InfoInteractor,
) : ViewModel() {

    private var searchDebounceJob: Job? = null

    private val stateLiveData =
        MutableLiveData<InfoScreenState>(InfoScreenState.Default)

    fun getStateLiveData(): LiveData<InfoScreenState> = stateLiveData

    private var latestSearchNumber: String? = null

    override fun onCleared() {
        searchDebounceJob?.cancel()
    }

    fun searchDebounce(changedNumber: String) {
        if (latestSearchNumber == changedNumber) {
            return
        }
        this.latestSearchNumber = changedNumber

        searchDebounceJob?.cancel()
        searchDebounceJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            searchInfo(changedNumber)
        }
    }

    private fun searchInfo(newSearchNumber: String) {
        if (newSearchNumber.isBlank()
        ) {  // чтобы после крестика не выскакивало, что ничего не нашлось
            return
        }

        searchDebounceJob?.cancel()  //чтобы не происходил повторный поиск при нажатии на галочку, потому не стала исп-ть debounce()

        renderState(InfoScreenState.Loading)

        viewModelScope.launch {
            infoInteractor.searchInfo(newSearchNumber.replace(" ", "").trim())
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(
        info: Info?,
        errorType: ErrorType?
    ) {
        if (info != null) {
            renderState(InfoScreenState.Content(info = info))
        }
        else if (errorType != null) {
                handleError(errorType)
            }
        else {
            renderState(InfoScreenState.Default)
        }
    }

    private fun renderState(state: InfoScreenState) {
        stateLiveData.postValue(state)
    }

    private fun handleError(errorType: ErrorType) {
        when (errorType) {
            ErrorType.CONNECTION_PROBLEM -> renderState(InfoScreenState.InternetError)
            ErrorType.NOTHING_FOUND -> renderState(InfoScreenState.NothingFound)
            ErrorType.LIMIT_ERROR -> renderState(InfoScreenState.LimitError)
            else -> renderState(InfoScreenState.ServerError)
        }
    }

    fun handleCoordinatesClick(context: Context, info: Info) {
        val latitude = info.countryLatitude
        val longitude = info.countryLongitude

        if (latitude != null && longitude != null) {
            val geoUri = "geo:$latitude,$longitude?q=$latitude,$longitude"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                Toast.makeText(context, R.string.no_maps_app_found, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, R.string.invalid_coordinates, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}