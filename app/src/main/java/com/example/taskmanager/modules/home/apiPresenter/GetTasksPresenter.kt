package com.example.taskmanager.modules.home.apiPresenter

import android.util.Log
import com.example.taskmanager.modules.home.interfaces.GetTasksInterface
import com.example.taskmanager.modules.home.models.GetTasksResponse
import com.example.taskmanager.network.NetworkState
import com.example.taskmanager.network.ResponseCode
import com.example.taskmanager.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

class GetTasksPresenter(val action: GetTasksInterface) {

    fun getTasks() {
        action.onGetTasksResponse(NetworkState.Loading)

        val apiResponseCall = RetrofitClient.instance.getTasks()

        apiResponseCall.enqueue(object : Callback<GetTasksResponse> {
            override fun onResponse(
                call: Call<GetTasksResponse>,
                response: Response<GetTasksResponse>
            ) {
                action.onGetTasksResponse(
                    if(response.isSuccessful){
                        val apiResponse = response.body()
                        Log.d("RESPONSE", "/////////////// response: $apiResponse")

                        NetworkState.Success(apiResponse)
                    }else NetworkState.Failure(
                        false,
                        response.code(),
                        response.errorBody(),
                        ResponseCode.checkApiResponse(response)
                    )
                )
            }

            override fun onFailure(call: Call<GetTasksResponse>, t: Throwable) {
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