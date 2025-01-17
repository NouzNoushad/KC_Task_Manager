package com.example.taskmanager.modules.home.models

import com.google.gson.annotations.SerializedName

data class DeleteTaskResponse(
    @field:SerializedName("message")
    val message: String? = null
)