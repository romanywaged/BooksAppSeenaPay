package com.romany.newspaperapp_seena.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.romany.newspaperapp_seena.data.repositories.DataRepository
import com.romany.newspaperapp_seena.utlis.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(private var dataRepository: DataRepository) :ViewModel() {

    private val dataStateFlow : MutableStateFlow<DataState>
            = MutableStateFlow(DataState.Empty)
    val stateFlowData: StateFlow<DataState> = dataStateFlow

    fun getSchedules() = viewModelScope.launch{
        dataStateFlow.value = DataState.Loading
        dataRepository.getAllData()
            .catch { e ->
                dataStateFlow.value = DataState.Failure(e)
            }
            .collect{
                dataStateFlow.value = DataState.Success(it)
            }
    }
}