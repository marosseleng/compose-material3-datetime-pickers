/*
 *    Copyright 2022 Maroš Šeleng
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
import com.marosseleng.compose.material3.datetimepickers.date.ui.DatePickerDialog
import com.marosseleng.compose.material3.datetimepickers.time.domain.noSeconds
import com.marosseleng.compose.material3.datetimepickers.time.ui.dialog.TimePickerDialog
import java.time.LocalDate
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
                        verticalArrangement = Arrangement.spacedBy(32.dp),
                    ) {
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            var isTimeDialogShown: Boolean by rememberSaveable {
                                mutableStateOf(false)
                            }
                            val (selectedTime, setSelectedTime) = rememberSaveable {
                                mutableStateOf(LocalTime.now().noSeconds())
                            }
                            if (isTimeDialogShown) {
                                TimePickerDialog(
                                    onDismissRequest = { isTimeDialogShown = false },
                                    initialTime = selectedTime,
                                    onTimeChange = {
                                        setSelectedTime(it)
                                        isTimeDialogShown = false
                                    },
                                    title = { Text(text = "Select time") }
                                )
                            }
                            Text(
                                text = "Selected time: $selectedTime",
                                style = MaterialTheme.typography.displaySmall,
                            )
                            FilledTonalButton(onClick = { isTimeDialogShown = true }) {
                                Text("Click me to change time")
                            }
                        }

                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            var isDateDialogShown: Boolean by rememberSaveable {
                                mutableStateOf(false)
                            }
                            val (selectedDate, setSelectedDate) = rememberSaveable {
                                mutableStateOf(LocalDate.now())
                            }
                            if (isDateDialogShown) {
                                DatePickerDialog(
                                    onDismissRequest = { isDateDialogShown = false },
                                    onDateChange = {
                                        setSelectedDate(it)
                                        isDateDialogShown = false
                                    },
                                    title = { Text(text = "Select time") },
                                )
                            }
                            Text(
                                text = "Selected Date: $selectedDate",
                                style = MaterialTheme.typography.displaySmall,
                            )
                            FilledTonalButton(onClick = { isDateDialogShown = true }) {
                                Text("Click me to change date")
                            }
                        }
                    }
                }
            }
        }
    }
}