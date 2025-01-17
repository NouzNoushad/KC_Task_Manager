package com.example.taskmanager.common

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun convertMillisToDate(millis: Long?): String {
    val calendar = Calendar.getInstance()
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = if(millis != null) Date(millis) else calendar.time
    return formatter.format(date)
}

fun changeStatusColor(status: String): Color {
   return when (status) {
        "pending" -> Color.Yellow
        "progress" -> Color.Blue
        "completed" -> Color.Green
        "canceled" -> Color.Red
        else -> Color.LightGray
    }
}