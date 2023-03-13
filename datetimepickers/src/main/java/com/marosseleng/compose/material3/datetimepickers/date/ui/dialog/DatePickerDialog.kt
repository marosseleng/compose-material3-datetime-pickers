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

package com.marosseleng.compose.material3.datetimepickers.date.ui.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.DialogProperties
import com.marosseleng.compose.material3.datetimepickers.common.domain.getDefaultLocale
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerColors
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerDefaults
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerShapes
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerTypography
import com.marosseleng.compose.material3.datetimepickers.date.ui.ModalDatePicker
import java.time.LocalDate
import java.util.Locale

/**
 * Displays the datepicker dialog for a single date election.
 *
 * @param onDismissRequest called when user wants to dismiss the dialog without selecting the time
 * @param onDateChange called when user selects the date and taps the positive button
 * @param modifier a [Modifier]
 * @param initialDate initial [LocalDate] to select or null
 * @param locale a [Locale] instance for displayed texts, such as Months names
 * @param today today
 * @param showDaysAbbreviations whether or not to show the row with days' abbreviations atop the day grid
 * @param highlightToday whether or not to highlight today
 * @param colors [DatePickerColors] to use
 * @param shapes [DatePickerShapes] to use
 * @param typography [DatePickerTypography] to use
 * @param title title of the dialog
 * @param buttonColors colors of dialog buttons
 */
@ExperimentalComposeUiApi
@Composable
public fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateChange: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    initialDate: LocalDate? = null,
    locale: Locale = LocalConfiguration.current.getDefaultLocale(),
    today: LocalDate = LocalDate.now(),
    showDaysAbbreviations: Boolean = true,
    highlightToday: Boolean = true,
    colors: DatePickerColors = DatePickerDefaults.colors(),
    shapes: DatePickerShapes = DatePickerDefaults.shapes(),
    typography: DatePickerTypography = DatePickerDefaults.typography(),
    title: @Composable (() -> Unit)? = null,
    buttonColors: ButtonColors = ButtonDefaults.textButtonColors(),
    shape: Shape = AlertDialogDefaults.shape,
    containerColor: Color = AlertDialogDefaults.containerColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
) {
    var date: LocalDate? by rememberSaveable(initialDate) {
        mutableStateOf(initialDate)
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                colors = buttonColors,
                onClick = { date?.also(onDateChange) },
                enabled = date != null,
            ) {
                Text(stringResource(id = android.R.string.ok))
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(
                colors = buttonColors,
                onClick = onDismissRequest,
            ) {
                Text(stringResource(id = android.R.string.cancel))
            }
        },
        title = null,
        icon = null,
        text = {
            ModalDatePicker(
                selectedDate = date,
                onDayClick = { date = it },
                title = title,
                modifier = Modifier,
                locale = locale,
                today = today,
                showDaysAbbreviations = showDaysAbbreviations,
                highlightToday = highlightToday,
                colors = colors,
                shapes = shapes,
                typography = typography,
            )
        },
        shape = shape,
        containerColor = containerColor,
        titleContentColor = titleContentColor,
        tonalElevation = tonalElevation,
        properties = properties,
    )
}