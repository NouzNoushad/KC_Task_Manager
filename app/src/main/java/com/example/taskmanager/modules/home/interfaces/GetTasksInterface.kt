package com.example.taskmanager.modules.home.interfaces

import com.example.taskmanager.modules.home.models.GetTasksResponse
import com.example.taskmanager.network.NetworkState

interface GetTasksInterface {
    fun onGetTasksResponse(response: NetworkState<GetTasksResponse>)
}