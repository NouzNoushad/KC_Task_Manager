package com.example.taskmanager.modules.home.apiPresenter

import android.util.Log
import com.example.taskmanager.modules.home.interfaces.DeleteTaskInterface
import com.example.taskmanager.modules.home.models.DeleteTaskResponse
import com.example.taskmanager.network.NetworkState
import com.example.taskmanager.network.ResponseCode
import com.example.taskmanager.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

class DeleteTaskPresenter(val action: DeleteTaskInterface) {
    fun deleteTask(
        id: String,
    ) {
        action.onDeleteTaskResponse(NetworkState.Loading)

        val apiCallResponse = RetrofitClient.instance.deleteTask(id)

        apiCallResponse.enqueue(object : Callback<DeleteTaskResponse> {
            override fun onResponse(
                call: Call<DeleteTaskResponse>,
                response: Response<DeleteTaskResponse>
            ) {
                action.onDeleteTaskResponse(
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

            override fun onFailure(call: Call<DeleteTaskResponse>, t: Throwable) {
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