package com.example.taskmanager.modules.createTask.apiPresenter

import android.util.Log
import com.example.taskmanager.common.TaskModel
import com.example.taskmanager.modules.createTask.interfaces.CreateTaskInterface
import com.example.taskmanager.modules.createTask.models.CreateTaskResponse
import com.example.taskmanager.network.NetworkState
import com.example.taskmanager.network.ResponseCode
import com.example.taskmanager.network.RetrofitClient
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateTaskPresenter(val action: CreateTaskInterface) {

    fun createTask(
        taskModel: TaskModel
    ) {
        action.onCreateTaskResponse(NetworkState.Loading)
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val defaultDate = formatter.format(calendar.time)
        val defaultStatus = "pending"
        val defaultCategory = "personal"

        val responseBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("title", taskModel.title ?: "")
            .addFormDataPart("description", taskModel.description ?: "")
            .addFormDataPart("status", taskModel.status ?: defaultStatus)
            .addFormDataPart("category", taskModel.category ?: defaultCategory)
            .addFormDataPart("start_date", taskModel.startDate ?: defaultDate)
            .addFormDataPart("due_date", taskModel.dueDate ?: defaultDate)
            .build()

        val apiResponseCall = RetrofitClient.instance.createTask(responseBody)

        apiResponseCall.enqueue(object : Callback<CreateTaskResponse> {
            override fun onResponse(
                call: Call<CreateTaskResponse>,
                response: Response<CreateTaskResponse>
            ) {
                action.onCreateTaskResponse(
                    if(response.isSuccessful){
                        val apiResponse = response.body()

                        NetworkState.Success(apiResponse)
                    }else NetworkState.Failure(
                        false,
                        response.code(),
                        response.errorBody(),
                        ResponseCode.checkApiResponse(response)
                    )
                )
            }

            override fun onFailure(call: Call<CreateTaskResponse>, t: Throwable) {
                when (t) {
                    is UnknownHostException, is ConnectException -> {
                        NetworkState.Failure(
                            true,
                            null,
                            null,
                            ResponseCode.NETWORK_FAILURE
                        )
                    }
                    else -> {
                        NetworkState.Failure(
                            false,
                            null,
                            null,
                            ResponseCode.CONVERSION_FAILURE)
                    }
                }
            }
        })
    }
}