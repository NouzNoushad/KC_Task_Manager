package com.example.taskmanager.modules.home.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.taskmanager.common.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold (
        topBar = {
            TopAppBar(title = { Text("Task Manager") },)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screens.CREATE_TASK_SCREEN.name) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ){  paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {

        }
    }
}