package com.example.taskmanager.modules.home.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.modules.home.apiPresenter.GetTasksPresenter
import com.example.taskmanager.modules.home.interfaces.GetTasksInterface
import com.example.taskmanager.modules.home.models.GetTasksResponse
import com.example.taskmanager.network.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GetTasksViewModel : ViewModel() {
    private val _mGetTasksResponse = MutableStateFlow<NetworkState<GetTasksResponse>>(NetworkState.Loading)
    val mGetTasksResponse = _mGetTasksResponse

    fun getTasks() {
        viewModelScope.launch {
            val getTasksPresenter = GetTasksPresenter(object : GetTasksInterface {
                override fun onGetTasksResponse(response: NetworkState<GetTasksResponse>) {
                    _mGetTasksResponse.value = response
                }
            })
            getTasksPresenter.getTasks()
        }
    }
}