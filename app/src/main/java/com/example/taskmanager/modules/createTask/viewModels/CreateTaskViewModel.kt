package com.example.taskmanager.modules.createTask.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.common.Constants
import com.example.taskmanager.common.TaskModel
import com.example.taskmanager.modules.createTask.apiPresenter.CreateTaskPresenter
import com.example.taskmanager.modules.createTask.interfaces.CreateTaskInterface
import com.example.taskmanager.modules.createTask.models.CreateTaskResponse
import com.example.taskmanager.network.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CreateTaskViewModel : ViewModel() {

    private val _mCreateTaskResponse = MutableStateFlow<NetworkState<CreateTaskResponse>>(NetworkState.Loading)
    val mCreateTaskResponse = _mCreateTaskResponse

    var title by  mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var selectedStatus by mutableStateOf(Constants.status[0])
        private set

    var isExpanded by mutableStateOf(false)
        private set

    var selectedCategory by mutableStateOf(Constants.categories[0])
        private set

    var selectedStartDate by mutableStateOf<Long?>(null)
        private set

    var selectedDueDate by mutableStateOf<Long?>(null)
        private set

    var showStartDateModal by mutableStateOf(false)
        private set

    var showDueDateModal by mutableStateOf(false)
        private set

    // set title
    fun onChangeTitle(newTitle: String) {
        title = newTitle
    }
    // set description
    fun onChangeDescription(newDescription: String) {
        description = newDescription
    }
    // set status
    fun onChangeStatus(status: String) {
        selectedStatus = status
    }
    // set status dropdown
    fun onExpandStatus(expanded: Boolean) {
        isExpanded = expanded
    }
    // set category
    fun onChangeCategory(category: String) {
        selectedCategory = category
    }
    // set start date
    fun onChangeStartDate(startDate: Long?) {
        selectedStartDate = startDate
    }
    // set due date
    fun onChangeDueDate(dueDate: Long?) {
        selectedDueDate = dueDate
    }
    // set start date model
    fun onChangeStartDateModal(startDateModal: Boolean) {
        showStartDateModal = startDateModal
    }
    // set due date
    fun onChangeDueDateModal(dueDateModal: Boolean) {
        showDueDateModal = dueDateModal
    }

    // create task
    fun createTask(
        taskModel: TaskModel,
    ) {
        viewModelScope.launch {
            val createTaskPresenter = CreateTaskPresenter(object : CreateTaskInterface {
                override fun onCreateTaskResponse(response: NetworkState<CreateTaskResponse>) {
                    _mCreateTaskResponse.value = response
                }
            })
            createTaskPresenter.createTask(taskModel)
        }
    }

    // reset task
    fun resetTask() {
        title = ""
        description = ""
        selectedStatus = Constants.status[0]
        isExpanded = false
        selectedCategory = Constants.categories[0]
        selectedStartDate = null
        selectedDueDate = null
        showStartDateModal = false
        showDueDateModal = false
    }
}