package com.example.taskmanager.modules.createTask.models

import com.example.taskmanager.common.TaskModel
import com.google.gson.annotations.SerializedName

data class CreateTaskResponse(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: TaskModel? = null
)
