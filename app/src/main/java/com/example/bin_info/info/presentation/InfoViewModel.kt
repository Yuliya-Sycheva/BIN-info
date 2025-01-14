package com.example.bin_info.info.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bin_info.external.domain.ExternalInteractor
import com.example.bin_info.history.domain.HistoryInteractor
import com.example.bin_info.info.domain.api.InfoInteractor
import com.example.bin_info.info.domain.model.ErrorType
import com.example.bin_info.info.domain.model.Info
import com.example.bin_info.info.ui.models.InfoScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InfoViewModel(
    private val infoInteractor: InfoInteractor,
    private val historyInteractor: HistoryInteractor,
    private val externalInteractor: ExternalInteractor,
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

    private suspend fun processResult(
        info: Info?,
        errorType: ErrorType?
    ) {
        if (info != null) {
            renderState(InfoScreenState.Content(info = info))
            historyInteractor.addInfoToHistory(info, latestSearchNumber!!)
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

    fun openMap(latitude : String, longitude: String) {
            externalInteractor.openMap(latitude, longitude)
    }

    fun openUrl(url: String) {
        externalInteractor.openUrl(url)
    }

    fun openPhone(phone: String) {
        externalInteractor.openPhone(phone)
    }

    companion object {
        const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}