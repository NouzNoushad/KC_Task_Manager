package com.example.taskmanager.network

import com.example.taskmanager.modules.createTask.models.CreateTaskResponse
import com.example.taskmanager.modules.home.models.GetTasksResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServiceInterface {

    @POST(Endpoints.CREATE_TASK)
    fun createTask(@Body map: RequestBody) : Call<CreateTaskResponse>

    @GET(Endpoints.GET_TASKS)
    fun getTasks() : Call<GetTasksResponse>
}