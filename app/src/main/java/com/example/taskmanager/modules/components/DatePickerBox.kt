package com.example.taskmanager.modules.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.common.convertMillisToDate

@Composable
fun DatePickerBox(
    label: String,
    selectedDate: Long?,
    onClick: () -> Unit,
) {
    Text(text = "$label Date")
    Spacer(modifier = Modifier.height(4.dp))
    Box(
        modifier = Modifier
            .clickable {
                onClick()
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
            Text(text = selectedDate.let {
                if (it != null) {
                    convertMillisToDate(it)
                } else {
                    convertMillisToDate(null)
                }
            }, style = TextStyle(
                fontSize = 16.sp
            )
            )
            Icon(Icons.Default.DateRange, contentDescription = "$label Date")
        }
    }
}