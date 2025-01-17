package com.example.taskmanager.modules.home.views

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.taskmanager.common.changeStatusColor
import com.example.taskmanager.modules.components.DeleteAlertDialog
import com.example.taskmanager.modules.home.models.GetTasksResponse
import com.example.taskmanager.modules.home.viewModels.DeleteTaskViewModel
import com.example.taskmanager.modules.home.viewModels.GetTasksViewModel
import com.example.taskmanager.network.NetworkState

@Composable
fun Tasks(
    navController: NavController,
    getTasksViewModel: GetTasksViewModel = viewModel(),
    deleteTasksViewModel: DeleteTaskViewModel = viewModel(),
) {
    val getTasksResponse by getTasksViewModel.mGetTasksResponse.collectAsState()
    val deleteTaskResponse by deleteTasksViewModel.mDeleteTaskResponse.collectAsState()
    val context = LocalContext.current
    val tasks = getTasksViewModel.tasks

    LaunchedEffect(getTasksResponse) {
        when (val state = getTasksResponse) {
            is NetworkState.Loading -> {}
            is NetworkState.Success -> {
                val tasksData = state.data?.data
                getTasksViewModel.onChangeTasks(tasksData ?: emptyList())
            }
            is NetworkState.Failure -> {
                Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(deleteTaskResponse) {
        when (val state = deleteTaskResponse) {
            is NetworkState.Loading -> {}
            is NetworkState.Success -> {
                val message = state.data?.message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                getTasksViewModel.getTasks()
            }
            is NetworkState.Failure -> {
                Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 10.dp, bottom = 20.dp, start = 15.dp, end = 15.dp)) {
        LazyColumn{
            items(tasks.size) {
                val task = tasks[it]
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .border(2.dp, Color.LightGray, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Column {
                        Row() {
                            Text(text = task.title ?: "",
                                style = TextStyle(fontSize = 17.sp,
                                    fontWeight = FontWeight.W600,),
                                modifier = Modifier.weight(1f))
                            Row() {
                                IconButton(onClick = { /*TODO*/ },
                                    modifier = Modifier.size(20.dp)) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                IconButton(onClick = {
                                    deleteTasksViewModel.onShowAlertDialog(true, task.id ?: "")
                                },modifier = Modifier.size(20.dp)) {
                                    Icon(Icons.Default.Delete, contentDescription = "Edit")
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = task.description ?: "")
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Box(
                                modifier = Modifier
                                    .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Text("${task.category}", style = TextStyle(
                                    fontSize = 13.sp
                                ))
                            }
                            Box(
                                modifier = Modifier
                                    .border(
                                        1.dp,
                                        changeStatusColor(task.status ?: ""),
                                        RoundedCornerShape(4.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Text("${task.status}", style = TextStyle(
                                    fontSize = 13.sp
                                ))
                            }
                        }
                    }
                }
            }
        }
    }

    if(deleteTasksViewModel.isShowAlertDialog) {
        DeleteAlertDialog(onDismissRequest = {
            deleteTasksViewModel.onShowAlertDialog(false, "")
        }) {
            val deleteId = deleteTasksViewModel.deleteId
            if(deleteId.isNotEmpty()) {
                deleteTasksViewModel.deleteTask(deleteId)
            }
            deleteTasksViewModel.onShowAlertDialog(false, "")
        }
    }
}