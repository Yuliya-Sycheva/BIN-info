package com.example.bin_info.history.presentation

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bin_info.history.domain.HistoryInteractor
import com.example.bin_info.info.domain.model.Info
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(private val interactor: HistoryInteractor) : ViewModel() {

    private val historyStateLiveData = MutableLiveData<HistoryState>()
    fun observeState(): LiveData<HistoryState> = historyStateLiveData

    fun fillData() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                interactor
                    .getHistory()
                    .collect { cardInfo ->
                        processResult(cardInfo)
                    }
            }
        } catch (ex: SQLiteException) {
            renderState(HistoryState.Error)
            Log.e("HistoryViewModel", "Ошибка: ${ex.message}")
        }
    }

    private fun processResult(history: List<Info>) {
        if (history.isEmpty()) {
            renderState(HistoryState.Empty)
        } else {
            renderState(HistoryState.Content(history))
        }
    }

    private fun renderState(state: HistoryState) = historyStateLiveData.postValue(state)
}