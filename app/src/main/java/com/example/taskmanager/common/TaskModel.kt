package com.example.taskmanager.common

import com.google.gson.annotations.SerializedName

data class TaskModel(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("start_date")
    val startDate: String? = null,

    @field:SerializedName("due_date")
    val dueDate: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,
)
