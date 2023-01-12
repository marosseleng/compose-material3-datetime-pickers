/*
 *    Copyright 2023 Maroš Šeleng
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

package com.marosseleng.compose.material3.datetimepickers.time.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.marosseleng.compose.material3.datetimepickers.common.domain.getDefaultLocale
import com.marosseleng.compose.material3.datetimepickers.time.domain.AmPmMode
import com.marosseleng.compose.material3.datetimepickers.time.domain.ClockDialMode
import com.marosseleng.compose.material3.datetimepickers.time.ui.dialog.TimePickerDialog
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@ShowkaseComposable(name = "TimePickerDialog", group = "timepicker")
@Composable
internal fun TimePickerDialogPreview() {
    TimePickerDialog(
        onDismissRequest = {},
        onTimeChange = {},
        initialTime = LocalTime.of(8, 9, 0, 0),
        is24HourFormat = false,
        title = { Text(text = "Select time") },
    )
}

@ShowkaseComposable(name = "HorizontalClockDigits", group = "timepicker")
@Composable
internal fun HorizontalClockDigitsPreview() {
    HorizontalClockDigits(
        time = LocalTime.of(17, 9),
        selectedMode = ClockDialMode.Minutes,
        amPmMode = AmPmMode.AM,
        onHourClick = {},
        onMinuteClick = {},
        onAmClick = {},
        onPmClick = {},
        locale = LocalConfiguration.current.getDefaultLocale(),
    )
}