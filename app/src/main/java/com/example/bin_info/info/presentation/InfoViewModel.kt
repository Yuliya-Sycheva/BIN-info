package com.example.bin_info.info.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        MutableLiveData<InfoScreenState>(InfoScreenState.Default)  //можно тут передать состояние по умолчанию??

    fun observeState(): LiveData<InfoScreenState> = stateLiveData

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

    fun searchInfo(newSearchNumber: String) {
        if (newSearchNumber.isBlank()
        ) {  // чтобы после крестика не выскакивало, что ничего не нашлось
            return
        }

        searchDebounceJob?.cancel()  //чтобы не происходил повторный поиск при нажатии на галочку, потому не стала исп-ть debounce()

        renderState(InfoScreenState.Loading)

        viewModelScope.launch {
            infoInteractor.searchInfo(newSearchNumber)
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

    fun onClearIconClick() {
        viewModelScope.launch {
            renderState(InfoScreenState.Default)
        }
    }

    companion object {
        const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}