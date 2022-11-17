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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marosseleng.compose.material3.datetimepickers.common.domain.applyIf
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerColors
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerDefaults
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerShapes
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerTypography
import com.marosseleng.compose.material3.datetimepickers.date.domain.LocalDatePickerColors
import com.marosseleng.compose.material3.datetimepickers.date.domain.LocalDatePickerShapes
import com.marosseleng.compose.material3.datetimepickers.date.domain.LocalDatePickerTypography
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale
import kotlin.math.ceil

internal enum class DatePickerMode {
    DOCKED,
}

public sealed interface DatePickerSelection {

    public data class Single(val value: LocalDate) : DatePickerSelection
    public data class Range(val from: LocalDate, val to: LocalDate) : DatePickerSelection
}

@Composable
public fun DatePicker(
    preSelectedDate: LocalDate?,
    onDateChange: (LocalDate?) -> Unit,
    modifier: Modifier = Modifier,
    highlightToday: Boolean = true,
    colors: DatePickerColors = DatePickerDefaults.colors,
    shapes: DatePickerShapes = DatePickerDefaults.shapes,
    typography: DatePickerTypography = DatePickerDefaults.typography,
) {
    var selectionFrom: LocalDate? by remember {
        mutableStateOf(null)
    }
    var selectionTo: LocalDate? by remember {
        mutableStateOf(null)
    }
    CompositionLocalProvider(
        LocalDatePickerColors provides colors,
        LocalDatePickerShapes provides shapes,
        LocalDatePickerTypography provides typography,
    ) {
        Month(
            month = YearMonth.now(),
            today = LocalDate.now(),
            locale = Locale.getDefault(),
            onDayClick = {
                if (selectionFrom == null) {
                    selectionFrom = it
                } else if (selectionTo != null) {
                    selectionFrom = it
                    selectionTo = null
                } else {
                    if (it < selectionFrom) {
                        selectionFrom = it
                        selectionTo = null
                    } else {
                        selectionTo = it
                    }
                }
            },
            showDaysAbbreviations = true,
            highlightToday = highlightToday,
            selectionFrom = selectionFrom,
            selectionTo = selectionTo
        )
    }
}

/**
 * selectionFrom <= selectionTo
 */
@Composable
internal fun WeekOfMonth(
    week: List<DayOfMonth>,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    currentMonth: YearMonth = YearMonth.now(),
    today: LocalDate = LocalDate.now(),
    highlightToday: Boolean = true,
    showPreviousMonth: Boolean = false,
    showNextMonth: Boolean = false,
    selectionFrom: LocalDate? = null,
    selectionTo: LocalDate? = null,
) {
    val horizontalRowPadding = 12.dp
    val dayBoxWidth = 40.dp

    val inRangeSelectionColor = LocalDatePickerColors.current.monthDayInRangeBackgroundColor
    val previousMonthTextColor = LocalDatePickerColors.current.previousMonthDayLabelTextColor
    val currentMonthUnselectedTextColor = LocalDatePickerColors.current.monthDayLabelUnselectedTextColor
    val currentMonthSelectedTextColor = LocalDatePickerColors.current.monthDayLabelSelectedTextColor
    val currentMonthSelectedBackgroundColor = LocalDatePickerColors.current.monthDayLabelSelectedBackgroundColor
    val currentMonthInRangeTextColor = LocalDatePickerColors.current.monthDayInRangeLabelTextColor
    val todayTextColor = LocalDatePickerColors.current.todayLabelTextColor
    val nextMonthTextColor = LocalDatePickerColors.current.nextMonthDayLabelTextColor
    Row(
        modifier = modifier
            .run {
                if (selectionFrom != null && selectionTo != null && selectionFrom != selectionTo) {
                    val mappedDays = week.map { it.actualDay }

                    val isStartBeforeWeek = selectionFrom < mappedDays.first()
                    val isStartAfterWeek = selectionFrom > mappedDays.last()

                    val isEndBeforeWeek = selectionTo < mappedDays.first()
                    val isEndAfterWeek = selectionTo > mappedDays.last()

                    if ((isStartBeforeWeek && isEndBeforeWeek) || (isStartAfterWeek && isEndAfterWeek)) {
                        // do nothing, selection is outside this week
                        return@run this
                    }

                    // at this point, there is some intersection in selection

                    val startDp = if (isStartBeforeWeek) {
                        0.dp
                    } else {
                        // isStartWithinWeek
                        horizontalRowPadding + (dayBoxWidth / 2) + (dayBoxWidth * mappedDays.indexOfFirst { it == selectionFrom })
                    }

                    val endDp = if (isEndAfterWeek) {
                        // full width
                        horizontalRowPadding * 2 + dayBoxWidth * 7
                    } else {
                        horizontalRowPadding + (dayBoxWidth / 2) + (dayBoxWidth * mappedDays.indexOfLast { it == selectionTo })
                    }

                    val startX = with(LocalDensity.current) { startDp.toPx() }
                    val endX = with(LocalDensity.current) { endDp.toPx() }

                    drawBehind {
                        drawRect(
                            inRangeSelectionColor,
                            topLeft = Offset(startX, 0f),
                            size = Size(width = endX - startX, height = size.height)
                        )
                    }
                } else {
                    this
                }
            }
            .padding(horizontal = horizontalRowPadding)
    ) {
        for (day in week) {
            // By priority: Selected > In Range > Today > Unselected
            val textColor = when {
                day.isPreviousMonth -> previousMonthTextColor
                day.isNextMonth -> nextMonthTextColor
                // is in range
                day.actualDay == selectionFrom -> currentMonthSelectedTextColor
                day.actualDay == selectionTo -> currentMonthSelectedTextColor
                selectionFrom != null && selectionTo != null && day.actualDay > selectionFrom && day.actualDay < selectionTo -> currentMonthInRangeTextColor
                day.isToday -> todayTextColor
                else -> currentMonthUnselectedTextColor
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .applyIf(day.actualDay == selectionFrom || day.actualDay == selectionTo) {
                        background(currentMonthSelectedBackgroundColor)
                    }
                    .clickable {
                        onDayClick(day.actualDay)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${day.actualDay.dayOfMonth}",
                    style = LocalDatePickerTypography.current.day,
                    color = textColor,
                )
            }
        }
    }
}

/**
 * Displays the grid of days within a given [month]. Also displays the days abbreviation if enabled.
 */
@Composable
internal fun Month(
    month: YearMonth,
    today: LocalDate,
    locale: Locale,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    showDaysAbbreviations: Boolean = true,
    highlightToday: Boolean = true,
    showPreviousMonth: Boolean = false,
    showNextMonth: Boolean = false,
    selectionFrom: LocalDate? = null,
    selectionTo: LocalDate? = null,
) {
    val globalFirstDay = WeekFields.of(locale).firstDayOfWeek
    val globalFirstDayIndex = globalFirstDay.value

    Column(modifier = modifier) {
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
                            style = LocalDatePickerTypography.current.weekDay,
                            color = LocalDatePickerColors.current.weekDayLabelTextColor,
                        )
                    }
                }
            }
        }

        for (week in getRowsToDisplay(month, globalFirstDay, today).chunked(7)) {
            WeekOfMonth(
                week = week,
                onDayClick = onDayClick,
                modifier = Modifier.padding(vertical = 4.dp),
                currentMonth = month,
                today = today,
                highlightToday = highlightToday,
                showPreviousMonth = showPreviousMonth,
                showNextMonth = showNextMonth,
                selectionFrom = selectionFrom,
                selectionTo = selectionTo,
            )
        }
    }
}

@Preview
@Composable
public fun DatePickerPreview() {
    DatePicker(
        preSelectedDate = LocalDate.now(),
        onDateChange = {},
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
    val isToday: Boolean,
)

internal fun getRowsToDisplay(yearMonth: YearMonth, firstDayOfWeek: DayOfWeek, today: LocalDate): List<DayOfMonth> {
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

    val result = mutableListOf<DayOfMonth>()
    if (difference != 0) {
        // first day of the month is NOT equal to the week's first day - there will be [difference] days from the past month
        for (i in difference downTo 1) {
            result.add(
                DayOfMonth(
                    actualDay = firstDayLocalDate.minusDays(i.toLong()),
                    isPreviousMonth = true,
                    isCurrentMonth = false,
                    isNextMonth = false,
                    isToday = false,
                )
            )
        }
    }

    var localDateToAdd = firstDayLocalDate
    while (YearMonth.from(localDateToAdd) == yearMonth) {
        result.add(
            DayOfMonth(
                actualDay = localDateToAdd,
                isPreviousMonth = false,
                isCurrentMonth = true,
                isNextMonth = false,
                isToday = localDateToAdd == today,
            )
        )
        localDateToAdd = localDateToAdd.plusDays(1L)
    }


    val resultSizeUpToSeven = ceil(result.size / 7.0).toInt() * 7
    val remainder = resultSizeUpToSeven - result.size
    if (remainder == 0) {
        return result
    }

    for (i in 0 until remainder) {
        result.add(
            DayOfMonth(
                actualDay = localDateToAdd.plusDays(i.toLong()),
                isPreviousMonth = false,
                isCurrentMonth = false,
                isNextMonth = true,
                isToday = false,
            )
        )
    }

    return result
}