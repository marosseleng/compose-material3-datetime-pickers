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

package com.marosseleng.compose.material3.datetimepickers.date.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerColors
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerDefaults
import com.marosseleng.compose.material3.datetimepickers.date.domain.LocalDatePickerColors
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale
import kotlin.math.ceil

@Composable
public fun DatePicker(
    preSelectedDate: LocalDate?,
    onDateChange: (LocalDate?) -> Unit,
    modifier: Modifier = Modifier,
    highlightToday: Boolean = true,
    colors: DatePickerColors = DatePickerDefaults.colors,
) {
    CompositionLocalProvider(
        LocalDatePickerColors provides colors
    ) {

    }
}

/**
 * Displays the grid of days within a given [month]. Also displays the days abbreviation if enabled.
 */
@Composable
public fun Month(month: YearMonth, locale: Locale, showDaysAbbreviations: Boolean, highlightToday: Boolean) {
    val globalFirstDay = WeekFields.of(locale).firstDayOfWeek
    val globalFirstDayIndex = globalFirstDay.value

    Column(modifier = Modifier) {
        if (showDaysAbbreviations) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            ) {
                for (dayIndex in 0 until 7) {
                    Box(
                        modifier = Modifier
                            .size(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val dayAbbr = DayOfWeek.of(((globalFirstDayIndex - 1 + dayIndex) % 7) + 1)
                            .getDisplayName(TextStyle.NARROW, Locale.getDefault())
                        Text(
                            text = dayAbbr,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
        }
        for (week in getRowsToDisplay(month, globalFirstDay).chunked(7)) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            ) {
                for (day in week) {
                    Box(
                        modifier = Modifier
                            .clickable { }
                            .size(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${day.dayOfMonth}",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
public fun MonthPreview() {
    Month(
        month = YearMonth.of(2022, java.time.Month.OCTOBER),
        locale = Locale("sk", "SK"),
        showDaysAbbreviations = true,
        highlightToday = true,
    )
}

public data class DateSelectionRange(
    override val start: LocalDate,
    override val endInclusive: LocalDate
) : ClosedRange<LocalDate>

public operator fun LocalDate.rangeTo(other: LocalDate): DateSelectionRange {
    return DateSelectionRange(this, other)
}

@Stable
internal data class DayOfMonth(
    val actualDay: LocalDate,
    val isPreviousMonth: Boolean,
    val isCurrentMonth: Boolean,
    val isNextMonth: Boolean,
    val isSelected: Boolean,
)

internal fun getRowsToDisplay(yearMonth: YearMonth, firstDayOfWeek: DayOfWeek): List<LocalDate> {
    val firstDayLocalDate = yearMonth.atDay(1)
    val firstDay = firstDayLocalDate.dayOfWeek

    val difference = if (firstDay == firstDayOfWeek) {
        // EZ
        0
    } else if (firstDay > firstDayOfWeek) {
        //
        firstDay.value - firstDayOfWeek.value
    } else {
        //
        7 - (firstDayOfWeek.value - firstDay.value)
    }

    val result = mutableListOf<LocalDate>()
    if (difference != 0) {
        // first day of the month is NOT equal to the week's first day - there will be [difference] days from the past month
        for (i in difference downTo 1) {
            result.add(firstDayLocalDate.minusDays(i.toLong()))
        }
    }

    var localDateToAdd = firstDayLocalDate
    while (YearMonth.from(localDateToAdd) == yearMonth) {
        result.add(localDateToAdd)
        localDateToAdd = localDateToAdd.plusDays(1L)
    }


    val resultSizeUpToSeven = ceil(result.size / 7.0).toInt() * 7
    val remainder = resultSizeUpToSeven - result.size
    if (remainder == 0) {
        return result
    }

    for (i in 0 until remainder) {
        result.add(localDateToAdd.plusDays(i.toLong()))
    }

    return result
}