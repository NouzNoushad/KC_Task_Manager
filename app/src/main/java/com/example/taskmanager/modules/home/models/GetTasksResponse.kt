package com.example.taskmanager.modules.home.models

import com.example.taskmanager.common.TaskModel
import com.google.gson.annotations.SerializedName

data class GetTasksResponse(
    @field:SerializedName("items")
    val items: String? = null,

    @field:SerializedName("data")
    val data: List<TaskModel>? = null
)
