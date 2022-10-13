package com.marosseleng.compose.material3.datetimepicker.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.marosseleng.compose.material3.datetimepicker.demo.ui.theme.ComposeMaterial3DatetimePickerTheme
import com.marosseleng.compose.material3.datetimepickers.time.domain.noSeconds
import com.marosseleng.compose.material3.datetimepickers.time.ui.dialog.TimePickerDialog
import java.time.LocalTime

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMaterial3DatetimePickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        var isDialogShown: Boolean by rememberSaveable {
                            mutableStateOf(false)
                        }
                        val (selectedTime, setSelectedTime) = rememberSaveable {
                            mutableStateOf(LocalTime.now().noSeconds())
                        }
                        if (isDialogShown) {
                            TimePickerDialog(
                                onDismissRequest = { isDialogShown = false },
                                initialTime = selectedTime,
                                onTimeChange = {
                                    setSelectedTime(it)
                                    isDialogShown = false
                                },
                                title = { Text(text = "Select time") }
                            )
                        }
                        Text(
                            text = "Selected time: $selectedTime",
                            style = MaterialTheme.typography.displaySmall,
                        )
                        FilledTonalButton(onClick = { isDialogShown = true }) {
                            Text("Click me to change time")
                        }
                    }
                }
            }
        }
    }
}