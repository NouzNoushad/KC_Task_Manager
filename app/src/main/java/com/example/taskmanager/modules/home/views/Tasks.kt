package com.example.taskmanager.modules.home.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.taskmanager.modules.home.models.GetTasksResponse
import com.example.taskmanager.modules.home.viewModels.GetTasksViewModel
import com.example.taskmanager.network.NetworkState

@Composable
fun Tasks(
    navController: NavController,
    getTasksViewModel: GetTasksViewModel = viewModel()
) {
    val getTasksResponse by getTasksViewModel.mGetTasksResponse.collectAsState()

    LaunchedEffect(Unit) {
        getTasksViewModel.getTasks()
    }

    LaunchedEffect(getTasksResponse) {
        when (val state = getTasksResponse) {
            is NetworkState.Loading -> {}
            is NetworkState.Success -> {}
            is NetworkState.Failure -> {}
        }
    }
}