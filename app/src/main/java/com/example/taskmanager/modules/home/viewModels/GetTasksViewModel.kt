package com.example.taskmanager.modules.home.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.common.TaskModel
import com.example.taskmanager.modules.home.apiPresenter.GetTasksPresenter
import com.example.taskmanager.modules.home.interfaces.GetTasksInterface
import com.example.taskmanager.modules.home.models.GetTasksResponse
import com.example.taskmanager.network.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GetTasksViewModel : ViewModel() {
    private val _mGetTasksResponse = MutableStateFlow<NetworkState<GetTasksResponse>>(NetworkState.Loading)
    val mGetTasksResponse = _mGetTasksResponse
    private var _tasks: List<TaskModel> by mutableStateOf<List<TaskModel>>(emptyList())
    val tasks: List<TaskModel>
        get() = _tasks

    // set tasks
    fun onChangeTasks(taskList: List<TaskModel>) {
        _tasks = taskList
    }

    init {
        getTasks()
    }

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