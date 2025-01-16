package com.example.taskmanager.modules.createTask.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun CreateTaskScreen(
   navController: NavController,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Task") },
                navigationIcon = {
                    Icon(Icons.Default.ArrowBack, contentDescription = "back")
                })
        }
    ) {paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            TaskForm(navController)
        }
    }
}