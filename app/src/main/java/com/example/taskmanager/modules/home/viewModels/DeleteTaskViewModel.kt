package com.example.taskmanager.modules.home.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.modules.home.apiPresenter.DeleteTaskPresenter
import com.example.taskmanager.modules.home.interfaces.DeleteTaskInterface
import com.example.taskmanager.modules.home.models.DeleteTaskResponse
import com.example.taskmanager.network.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeleteTaskViewModel : ViewModel() {
    private val _mDeleteTaskResponse = MutableStateFlow<NetworkState<DeleteTaskResponse>>(NetworkState.Loading)
    val mDeleteTaskResponse : StateFlow<NetworkState<DeleteTaskResponse>> = _mDeleteTaskResponse
    private var _deleteId: String by mutableStateOf("")
    private var _isShowAlertDialog: Boolean by mutableStateOf(false)
    val deleteId: String
        get() = _deleteId
    val isShowAlertDialog: Boolean
        get() = _isShowAlertDialog

    fun onShowAlertDialog(isShow: Boolean, id: String) {
        _isShowAlertDialog = isShow
        _deleteId = id
    }

    fun deleteTask(
        id: String
    ) {
        viewModelScope.launch {
            val deleteTaskPresenter = DeleteTaskPresenter(object : DeleteTaskInterface {
                override fun onDeleteTaskResponse(response: NetworkState<DeleteTaskResponse>) {
                    _mDeleteTaskResponse.value = response
                }
            })
            deleteTaskPresenter.deleteTask(id)
        }
    }
}