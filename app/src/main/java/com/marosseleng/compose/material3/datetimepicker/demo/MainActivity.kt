package com.marosseleng.compose.material3.datetimepicker.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.marosseleng.compose.material3.datetimepicker.demo.ui.theme.ComposeMaterial3DatetimePickerTheme
import com.marosseleng.compose.material3.datetimepickers.time.ui.dialog.TimePickerDialog
import java.time.LocalTime

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMaterial3DatetimePickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        var isDialogShown: Boolean by rememberSaveable {
                            mutableStateOf(false)
                        }
                        val (selectedTime, setSelectedTime) = rememberSaveable {
                            mutableStateOf(LocalTime.now())
                        }
                        if (isDialogShown) {
                            TimePickerDialog(
                                onDismissRequest = { isDialogShown = false },
                                onTimeChange = {
                                    setSelectedTime(it)
                                    isDialogShown = false
                                },
                                title = { Text(text = "Select time") }
                            )
                        }
                        Text(text = "Selected time: $selectedTime")
                        ElevatedButton(onClick = { isDialogShown = true }) {
                            Text("Show dialog")
                        }
                    }
                }
            }
        }
    }
}