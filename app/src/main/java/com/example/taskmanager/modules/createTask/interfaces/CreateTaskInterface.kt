package com.example.taskmanager.modules.createTask.interfaces

import com.example.taskmanager.modules.createTask.models.CreateTaskResponse
import com.example.taskmanager.network.NetworkState

interface CreateTaskInterface {
    fun onCreateTaskResponse(response: NetworkState<CreateTaskResponse>)
}