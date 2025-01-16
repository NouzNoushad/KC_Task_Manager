package com.example.taskmanager.modules.createTask.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.taskmanager.common.Constants
import com.example.taskmanager.common.TaskModel
import com.example.taskmanager.common.convertMillisToDate
import com.example.taskmanager.modules.components.DatePickerBox
import com.example.taskmanager.modules.components.DatePickerModal
import com.example.taskmanager.modules.components.TaskTextField
import com.example.taskmanager.modules.createTask.viewModels.CreateTaskViewModel
import com.example.taskmanager.network.NetworkState
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TaskForm(
    navController: NavController,
    createTaskViewModel: CreateTaskViewModel = viewModel()
) {
    val title = createTaskViewModel.title
    val description = createTaskViewModel.description
    val selectedStatus = createTaskViewModel.selectedStatus
    val isExpanded = createTaskViewModel.isExpanded
    val selectedCategory = createTaskViewModel.selectedCategory
    val selectedStartDate = createTaskViewModel.selectedStartDate
    val selectedDueDate = createTaskViewModel.selectedDueDate
    val showStartDateModal = createTaskViewModel.showStartDateModal
    val showDueDateModal = createTaskViewModel.showDueDateModal

    val context = LocalContext.current

    val createTaskResponse by createTaskViewModel.mCreateTaskResponse.collectAsState()

    LaunchedEffect(createTaskResponse) {
        when (val status = createTaskResponse) {
            is NetworkState.Loading -> {}
            is NetworkState.Success -> {
                val message = status.data?.message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                createTaskViewModel.resetTask()
                navController.popBackStack()
            }
            is NetworkState.Failure -> {
                Toast.makeText(context, status.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 20.dp),
    ) {
        LazyColumn {
            item {
                // title
                TaskTextField(title = title,
                    placeholder = "Title",
                    onValueChange = { createTaskViewModel.onChangeTitle(it) })
                Spacer(modifier = Modifier.height(8.dp))
                // description
                TaskTextField(title = description,
                    placeholder = "Description",
                    onValueChange = { createTaskViewModel.onChangeDescription(it)},
                    singleLine = false)
                Spacer(modifier = Modifier.height(8.dp))
                // status dropdown
                Box(
                    modifier = Modifier
                        .clickable {
                            createTaskViewModel.onExpandStatus(!isExpanded)
                        }
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .border(2.dp, Color.Gray, shape = RoundedCornerShape(10.dp))
                            .padding(15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = selectedStatus.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        }, style = TextStyle(
                            fontSize = 16.sp
                        )
                        )
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "status")
                    }

                    DropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { createTaskViewModel.onExpandStatus(false) },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .background(Color.White)) {
                        Constants.status.forEach { item ->
                            DropdownMenuItem(text = { Text(
                                item.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }, style = TextStyle(
                                fontSize = 16.sp)
                            ) }, onClick = {
                                createTaskViewModel.onChangeStatus(item)
                                createTaskViewModel.onExpandStatus(false)
                            })
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                // categories
                LazyVerticalGrid(columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .border(2.dp, Color.Gray, shape = RoundedCornerShape(10.dp)),
                    userScrollEnabled = false,
                    contentPadding = PaddingValues(10.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    items(Constants.categories.size) { index ->
                        val category = Constants.categories[index]
                        Box(modifier = Modifier
                            .height(60.dp)
                            .padding(3.dp)
                            .border(
                                if (selectedCategory == category) 2.8.dp else 2.dp,
                                if (selectedCategory == category) Color.DarkGray else Color.Gray.copy(
                                    0.5f
                                ),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                createTaskViewModel.onChangeCategory(category)
                            },
                            contentAlignment = Alignment.Center){
                            Text(text = category.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.ROOT
                                ) else it.toString()
                            }, modifier = Modifier.padding(5.dp),
                                style = TextStyle(fontSize = 13.sp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                // start date
                DatePickerBox(label = "Start", selectedDate = selectedStartDate) {
                    createTaskViewModel.onChangeStartDateModal(true)
                }
                Spacer(modifier = Modifier.height(8.dp))
                // due date
                DatePickerBox(label = "Due", selectedDate = selectedDueDate) {
                    createTaskViewModel.onChangeDueDateModal(true)
                }
                Spacer(modifier = Modifier.height(30.dp))
                // Add button
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(onClick = {
                        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val startDate = formatter.parse(convertMillisToDate(selectedStartDate))
                        val dueDate = formatter.parse(convertMillisToDate(selectedDueDate))

                        if(title.isEmpty()) {
                            Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                        }
                        else if (startDate != null && dueDate != null) {
                            if(startDate.time > dueDate.time) {
                                Toast.makeText(context, "Start date cannot be greater than due date", Toast.LENGTH_SHORT).show()
                            } else {
                                val taskModel = TaskModel(
                                    title = title,
                                    description = description,
                                    status = selectedStatus,
                                    category = selectedCategory,
                                    startDate = if(selectedStartDate != null) convertMillisToDate(selectedStartDate) else convertMillisToDate(null),
                                    dueDate = if(selectedDueDate != null) convertMillisToDate(selectedDueDate) else convertMillisToDate(null)
                                )
                                Log.d("task", "//////////////////// task -> " +
                                        "title: ${taskModel.title}, " +
                                        "description: ${taskModel.description}, " +
                                        "status: ${taskModel.status}, " +
                                        "category: ${taskModel.category}, " +
                                        "startDate: ${taskModel.startDate}, " +
                                        "dueDate: ${taskModel.dueDate}")

                                createTaskViewModel.createTask(taskModel)
                            }
                        }
                    },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(55.dp)) {
                        Text("Add Task", style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500
                        )
                        )
                    }
                }
            }
        }
    }

    if(showStartDateModal) {
        DatePickerModal(onDateSelected = { createTaskViewModel.onChangeStartDate(it)},
            onDismiss = { createTaskViewModel.onChangeStartDateModal(false)})
    }

    if(showDueDateModal) {
        DatePickerModal(onDateSelected = { createTaskViewModel.onChangeDueDate(it)},
            onDismiss = { createTaskViewModel.onChangeDueDateModal(false)})
    }
}