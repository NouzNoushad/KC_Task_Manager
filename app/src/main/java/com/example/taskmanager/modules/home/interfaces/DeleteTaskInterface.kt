package com.example.taskmanager.modules.home.interfaces

import com.example.taskmanager.modules.home.models.DeleteTaskResponse
import com.example.taskmanager.network.NetworkState

interface DeleteTaskInterface {
    fun onDeleteTaskResponse(response: NetworkState<DeleteTaskResponse>)
}