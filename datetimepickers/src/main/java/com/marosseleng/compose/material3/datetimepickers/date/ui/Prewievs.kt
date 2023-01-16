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

package com.marosseleng.compose.material3.datetimepickers.date.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalConfiguration
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.marosseleng.compose.material3.datetimepickers.common.domain.getDefaultLocale
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalComposeUiApi::class)
@ShowkaseComposable(name = "DatePickerDialog", group = "datepicker")
@Composable
internal fun DatePickerDialogPreview() {
    DatePickerDialog(
        onDismissRequest = {},
        onDateChange = {},
        initialDate = LocalDate.of(2023, java.time.Month.FEBRUARY, 28),
        today = LocalDate.of(2023, java.time.Month.FEBRUARY, 23),
        showDaysAbbreviations = true,
        highlightToday = true,
        title = { Text("Select date") },
    )
}

@ShowkaseComposable(name = "MonthSelection", group = "datepicker", heightDp = 360, widthDp = 280)
@Composable
internal fun MonthSelectionPreview() {
    MonthSelection(
        selectedMonth = java.time.Month.MARCH,
        onMonthClick = {},
        locale = LocalConfiguration.current.getDefaultLocale(),
    )
}

@ShowkaseComposable(name = "YearSelection", group = "datepicker", heightDp = 360, widthDp = 280)
@Composable
internal fun YearSelectionPreview() {
    YearSelection(selectedYear = 2023, onYearClick = {})
}

@ShowkaseComposable(name = "Month", group = "datepicker", heightDp = 360, widthDp = 280)
@Composable
internal fun MonthPreview() {
    Month(
        month = YearMonth.of(2023, java.time.Month.FEBRUARY),
        onDayClick = {},
        locale = LocalConfiguration.current.getDefaultLocale(),
        onSwipeRightLeft = {},
        onSwipeLeftRight = {},
        today = LocalDate.of(2023, java.time.Month.FEBRUARY, 21),
        showDaysAbbreviations = true,
        highlightToday = true,
        showPreviousMonth = true,
        showNextMonth = true,
        selectionFrom = LocalDate.of(2023, java.time.Month.FEBRUARY, 4),
        selectionTo = LocalDate.of(2023, java.time.Month.FEBRUARY, 17),
    )
}