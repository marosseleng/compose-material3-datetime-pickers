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

package com.marosseleng.compose.material3.datetimepickers.time.ui.dialog

import android.text.format.DateFormat
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.DialogProperties
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.marosseleng.compose.material3.datetimepickers.common.domain.getDefaultLocale
import com.marosseleng.compose.material3.datetimepickers.time.domain.TimePickerColors
import com.marosseleng.compose.material3.datetimepickers.time.domain.TimePickerDefaults
import com.marosseleng.compose.material3.datetimepickers.time.domain.TimePickerShapes
import com.marosseleng.compose.material3.datetimepickers.time.domain.TimePickerTypography
import com.marosseleng.compose.material3.datetimepickers.time.domain.noSeconds
import com.marosseleng.compose.material3.datetimepickers.time.ui.TimePicker
import java.time.LocalTime
import java.util.Locale

/**
 * Dialog containing time picker. This is an example minimal implementations.
 *
 * Users are free to create their own to suit their needs.
 *
 * @param onDismissRequest called when user wants to dismiss the dialog without selecting the time.
 * @param onTimeChange called when user taps the confirmation button.
 * @param initialTime initial time to show in the picker.
 * @param is24HourFormat whether or not the time picker should be shown in 24-hour format.
 */
@ExperimentalMaterial3Api
@Composable
public fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeChange: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    initialTime: LocalTime = LocalTime.now().noSeconds(),
    locale: Locale = LocalConfiguration.current.getDefaultLocale(),
    is24HourFormat: Boolean = DateFormat.is24HourFormat(LocalContext.current),
    colors: TimePickerColors = TimePickerDefaults.colors,
    shapes: TimePickerShapes = TimePickerDefaults.shapes,
    typography: TimePickerTypography = TimePickerDefaults.typography,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    shape: Shape = AlertDialogDefaults.shape,
    containerColor: Color = AlertDialogDefaults.containerColor,
    iconContentColor: Color = AlertDialogDefaults.iconContentColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    textContentColor: Color = AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties(),
) {
    var time by rememberSaveable(initialTime) {
        mutableStateOf(initialTime)
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = { onTimeChange(time) }) {
                Text(stringResource(id = android.R.string.ok))
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(id = android.R.string.cancel))
            }
        },
        icon = icon,
        title = title,
        text = {
            TimePicker(
                initialTime = initialTime,
                onTimeChange = { time = it },
                locale = locale,
                is24HourFormat = is24HourFormat,
                colors = colors,
                shapes = shapes,
                typography = typography,
                modifier = Modifier.verticalScroll(rememberScrollState()),
            )
        },
        shape = shape,
        containerColor = containerColor,
        iconContentColor = iconContentColor,
        titleContentColor = titleContentColor,
        textContentColor = textContentColor,
        tonalElevation = tonalElevation,
        properties = properties,
    )
}

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