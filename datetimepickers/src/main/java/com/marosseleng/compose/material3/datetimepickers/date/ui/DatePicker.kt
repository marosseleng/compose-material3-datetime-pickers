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

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.marosseleng.compose.material3.datetimepickers.R
import com.marosseleng.compose.material3.datetimepickers.common.domain.getDefaultLocale
import com.marosseleng.compose.material3.datetimepickers.common.domain.withNotNull
import com.marosseleng.compose.material3.datetimepickers.common.ui.BidirectionalInfiniteListHandler
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerColors
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerDefaults
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerShapes
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerStroke
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerTypography
import com.marosseleng.compose.material3.datetimepickers.date.domain.LocalDatePickerColors
import com.marosseleng.compose.material3.datetimepickers.date.domain.LocalDatePickerShapes
import com.marosseleng.compose.material3.datetimepickers.date.domain.LocalDatePickerTypography
import com.marosseleng.compose.material3.datetimepickers.date.domain.SelectionMode
import com.marosseleng.compose.material3.datetimepickers.date.domain.getDaysToDisplay
import com.marosseleng.compose.material3.datetimepickers.date.domain.getDisplayName
import com.marosseleng.compose.material3.datetimepickers.date.domain.getYears
import com.marosseleng.compose.material3.datetimepickers.time.domain.DayOfMonth
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

/**
 * Core composable, representing the Modal datepicker.
 *
 * @param selectedDate currently selected [LocalDate] or `null`
 * @param onDayClick called when the selected [LocalDate] changes
 * @param modifier a [Modifier]
 * @param locale [Locale] for printing names of months
 * @param today today
 * @param showDaysAbbreviations whether or not to show the days' abbreviations row
 * @param highlightToday whether or not to highlight today in the grid
 * @param colors [DatePickerColors] to use
 * @param shapes [DatePickerShapes] to use
 * @param typography [DatePickerTypography] to use
 */
@Composable
internal fun ModalDatePicker(
    selectedDate: LocalDate?,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    locale: Locale = LocalConfiguration.current.getDefaultLocale(),
    today: LocalDate = LocalDate.now(),
    showDaysAbbreviations: Boolean = true,
    highlightToday: Boolean = true,
    colors: DatePickerColors = DatePickerDefaults.colors,
    shapes: DatePickerShapes = DatePickerDefaults.shapes,
    typography: DatePickerTypography = DatePickerDefaults.typography,
) {
    var yearMonth by rememberSaveable(selectedDate ?: today) {
        mutableStateOf(YearMonth.of(selectedDate?.year ?: today.year, selectedDate?.month ?: today.month))
    }
    var mode: SelectionMode by rememberSaveable {
        mutableStateOf(SelectionMode.DAY)
    }

    CompositionLocalProvider(
        LocalDatePickerColors provides colors,
        LocalDatePickerShapes provides shapes,
        LocalDatePickerTypography provides typography,
    ) {
        Column(
            modifier = modifier
                .widthIn(max = 280.dp)
        ) {
            MonthYearSelection(
                currentYearMonth = yearMonth,
                dropdownOpen = mode == SelectionMode.DAY,
                onPreviousMonthClick = { yearMonth = yearMonth.minusMonths(1L) },
                onMonthClick = {
                    mode = if (mode == SelectionMode.DAY) {
                        SelectionMode.YEAR
                    } else {
                        SelectionMode.DAY
                    }
                },
                onNextMonthClick = { yearMonth = yearMonth.plusMonths(1L) },
                modifier = Modifier.offset(x = -16.dp),
            )

            Crossfade(
                modifier = Modifier
                    .animateContentSize(),
                targetState = mode,
            ) { state ->
                when (state) {
                    SelectionMode.DAY -> {
                        Month(
                            modifier = Modifier
                                .padding(top = 16.dp),
                            selectionFrom = selectedDate,
                            selectionTo = selectedDate,
                            month = yearMonth,
                            today = today,
                            locale = locale,
                            onDayClick = onDayClick,
                            showDaysAbbreviations = showDaysAbbreviations,
                            highlightToday = highlightToday,
                            showPreviousMonth = false,
                            showNextMonth = false,
                        )
                    }

                    SelectionMode.YEAR -> {
                        YearSelection(
                            modifier = Modifier
                                .heightIn(max = 280.dp),
                            selectedYear = yearMonth.year,
                            onYearClick = {
                                yearMonth = YearMonth.of(it, yearMonth.month)
                                mode = SelectionMode.MONTH
                            },
                        )
                    }

                    SelectionMode.MONTH -> {
                        MonthSelection(
                            modifier = Modifier
                                .heightIn(max = 336.dp),
                            locale = LocalConfiguration.current.getDefaultLocale(),
                            selectedMonth = yearMonth.month,
                            onMonthClick = {
                                yearMonth = YearMonth.of(yearMonth.year, it)
                                mode = SelectionMode.DAY
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Displays grid for month selection.
 *
 * @param selectedMonth the currently selected [Month]
 * @param onMonthClick called when a month is clicked
 * @param modifier a [Modifier]
 * @param locale [Locale] for formatting [selectedMonth] and other [Month]s
 */
@Composable
internal fun MonthSelection(
    selectedMonth: Month,
    onMonthClick: (Month) -> Unit,
    modifier: Modifier = Modifier,
    locale: Locale = LocalConfiguration.current.getDefaultLocale()
) {
    val months by remember(Unit) {
        mutableStateOf(Month.values().map { it.getDisplayName(TextStyle.FULL_STANDALONE, locale) })
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (y in 0..5) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                for (month in listOf((2 * y), (2 * y + 1))) {
                    val textColor: Color
                    val backgroundColor: Color
                    val stroke: DatePickerStroke?

                    if (month == selectedMonth.value - 1) {
                        textColor = LocalDatePickerColors.current.selectedMonthTextColor
                        backgroundColor = LocalDatePickerColors.current.selectedMonthBackgroundColor
                        stroke = LocalDatePickerColors.current.selectedMonthStroke
                    } else {
                        textColor = LocalDatePickerColors.current.unselectedMonthBackgroundColor
                        backgroundColor = LocalDatePickerColors.current.unselectedMonthBackgroundColor
                        stroke = LocalDatePickerColors.current.unselectedMonthStroke
                    }

                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .weight(1f)
                            .clip(LocalDatePickerShapes.current.month)
                            .withNotNull(stroke) {
                                border(it.thickness, it.color)
                            }
                            .background(backgroundColor)
                            .clickable {
                                onMonthClick(Month.of(month + 1))
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            modifier = Modifier,
                            text = months[month],
                            style = LocalDatePickerTypography.current.month,
                            color = textColor,
                        )
                    }
                }
            }
        }
    }
}

/**
 * Displays grid for year selection.
 *
 * @param selectedYear the currently selected year
 * @param onYearClick called when a year is clicked
 * @param modifier a [Modifier]
 */
@Composable
internal fun YearSelection(
    selectedYear: Int,
    onYearClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    var initialLess by remember {
        mutableStateOf(1)
    }
    var initialMore by remember {
        mutableStateOf(7)
    }

    val items by remember {
        derivedStateOf {
            getYears(yearsPerRow = 3, rowsLess = initialLess, rowsMore = initialMore)
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyListState,
    ) {
        items(items, key = { it }) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                it.forEach { year ->
                    val textColor: Color
                    val backgroundColor: Color
                    val stroke: DatePickerStroke?

                    if (year == selectedYear) {
                        textColor = LocalDatePickerColors.current.selectedYearTextColor
                        backgroundColor = LocalDatePickerColors.current.selectedYearBackgroundColor
                        stroke = LocalDatePickerColors.current.selectedYearStroke
                    } else {
                        textColor = LocalDatePickerColors.current.unselectedYearBackgroundColor
                        backgroundColor = LocalDatePickerColors.current.unselectedYearBackgroundColor
                        stroke = LocalDatePickerColors.current.unselectedYearStroke
                    }

                    Box(
                        modifier = Modifier
                            .size(width = 72.dp, height = 36.dp)
                            .clip(LocalDatePickerShapes.current.year)
                            .withNotNull(stroke) {
                                border(it.thickness, it.color)
                            }
                            .background(backgroundColor)
                            .clickable {
                                onYearClick(year)
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = year.toString(),
                            style = LocalDatePickerTypography.current.year,
                            color = textColor,
                        )
                    }
                }
            }
        }
    }

    BidirectionalInfiniteListHandler(
        listState = lazyListState,
        threshold = 2,
        onLoadPrevious = { initialLess += 5 },
        onLoadNext = { initialMore += 5 },
    )
}

/**
 * Displays "row" with month/year selection.
 *
 * @param currentYearMonth the current [YearMonth] displayed in datepicker
 * @param dropdownOpen whether the dropdown is open
 * @param onPreviousMonthClick called when clicked on the "previous month" arrow
 * @param onMonthClick called when clicked on the current month
 * @param onNextMonthClick called when clicked on the "next month" arrow
 * @param modifier a [Modifier]
 * @param locale [Locale] for formatting [currentYearMonth]
 */
@Composable
internal fun MonthYearSelection(
    currentYearMonth: YearMonth,
    dropdownOpen: Boolean,
    onPreviousMonthClick: () -> Unit,
    onMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    modifier: Modifier = Modifier,
    locale: Locale = LocalConfiguration.current.getDefaultLocale()
) {
    val iconRotation by animateFloatAsState(targetValue = if (dropdownOpen) 0F else 180f)

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .height(32.dp)
                .clip(RoundedCornerShape(percent = 50))
                .clickable(onClick = onMonthClick),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = currentYearMonth.getDisplayName(locale),
                style = LocalDatePickerTypography.current.monthYear,
                color = LocalDatePickerColors.current.yearMonthTextColor,
            )
            Icon(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .rotate(iconRotation),
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = stringResource(id = R.string.datepicker_select_month_year),
                tint = LocalDatePickerColors.current.yearMonthTextColor,
            )
        }
        Row {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onPreviousMonthClick),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.datepicker_previous_month),
                    tint = LocalDatePickerColors.current.previousNextMonthIconColor,
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onNextMonthClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.datepicker_next_month),
                    modifier = Modifier.size(24.dp),
                    tint = LocalDatePickerColors.current.previousNextMonthIconColor,
                )
            }
        }
    }
}

/**
 * Displays the grid of days within a given [month]. Also displays the days abbreviation if enabled.
 *
 * @param month a [Month] to display
 * @param onDayClick called when a day is clicked within this [Month]
 * @param modifier a [Modifier]
 * @param locale [Locale] which to take first day of week from
 * @param today today
 * @param showDaysAbbreviations whether to show the row with day names abbreviations atop the grid
 */
@Composable
internal fun Month(
    month: YearMonth,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    locale: Locale = LocalConfiguration.current.getDefaultLocale(),
    today: LocalDate = LocalDate.now(),
    showDaysAbbreviations: Boolean = true,
    highlightToday: Boolean = true,
    showPreviousMonth: Boolean = false,
    showNextMonth: Boolean = false,
    selectionFrom: LocalDate? = null,
    selectionTo: LocalDate? = null,
) {
    val globalFirstDay = WeekFields.of(locale).firstDayOfWeek
    val globalFirstDayIndex = globalFirstDay.value

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {

        if (showDaysAbbreviations) {
            Row(
                modifier = Modifier
            ) {
                for (dayIndex in 0 until 7) {
                    Box(
                        modifier = Modifier
                            .size(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val dayAbbr = DayOfWeek.of(((globalFirstDayIndex - 1 + dayIndex) % 7) + 1)
                            .getDisplayName(TextStyle.NARROW, locale)
                        Text(
                            text = dayAbbr,
                            style = LocalDatePickerTypography.current.weekDay,
                            color = LocalDatePickerColors.current.weekDayLabelTextColor,
                        )
                    }
                }
            }
        }

        for (week in getDaysToDisplay(month, globalFirstDay).chunked(7)) {
            WeekOfMonth(
                week = week,
                onDayClick = onDayClick,
                today = today,
                modifier = Modifier.padding(vertical = 4.dp),
                highlightToday = highlightToday,
                showPreviousMonth = showPreviousMonth,
                showNextMonth = showNextMonth,
                selectionFrom = selectionFrom,
                selectionTo = selectionTo,
            )
        }
    }
}

/**
 * Displays single week of month. In other words, a single row in the grid.
 *
 * @param week list of [DayOfMonth] to display
 * @param onDayClick called when a [DayOfMonth] is clicked
 * @param today today
 * @param modifier Modifier
 * @param highlightToday whether to highlight today
 * @param showPreviousMonth whether to display the previous month
 * @param showNextMonth whether to display the next month
 * @param selectionFrom the start [LocalDate] of the selection or `null` if no selection is active
 * @param selectionTo the end [LocalDate] of the selection or `null` if no selection is active
 */
@Composable
internal fun WeekOfMonth(
    week: List<DayOfMonth>,
    onDayClick: (LocalDate) -> Unit,
    today: LocalDate,
    modifier: Modifier = Modifier,
    highlightToday: Boolean = true,
    showPreviousMonth: Boolean = false,
    showNextMonth: Boolean = false,
    selectionFrom: LocalDate? = null,
    selectionTo: LocalDate? = null,
) {
    val dayBoxWidth = 40.dp

    val inRangeSelectionColor = LocalDatePickerColors.current.monthDayInRangeBackgroundColor
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
                        (dayBoxWidth / 2) + (dayBoxWidth * mappedDays.indexOfFirst { it == selectionFrom })
                    }

                    val endDp = if (isEndAfterWeek) {
                        // full width
                        dayBoxWidth * 7
                    } else {
                        (dayBoxWidth / 2) + (dayBoxWidth * mappedDays.indexOfLast { it == selectionTo })
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
    ) {
        for (day in week) {
            if (day.isPreviousMonth) {
                PreviousMonthDay(
                    day = day,
                    onDayClick = onDayClick,
                    shouldBeDisplayed = showPreviousMonth,
                )
            } else if (day.isNextMonth) {
                NextMonthDay(
                    day = day,
                    onDayClick = onDayClick,
                    shouldBeDisplayed = showNextMonth,
                )
            } else {
                CurrentMonthDay(
                    day = day,
                    onDayClick = onDayClick,
                    isToday = day.actualDay == today,
                    highlightToday = highlightToday,
                    isSelected = day.actualDay == selectionFrom || day.actualDay == selectionTo,
                    isInSelectionRange = selectionFrom != null && selectionTo != null &&
                            day.actualDay > selectionFrom && day.actualDay < selectionTo
                )
            }
        }
    }
}

/**
 * Displays a day in a grid.
 *
 * If [shouldBeDisplayed] is `false`, an empty box is displayed.
 *
 * @param day a day to display
 * @param onDayClick called when [day] is clicked
 * @param shouldBeDisplayed whether or not [day] should be displayed
 * @param textColor [Color] of this [day]'s text
 * @param backgroundColor background [Color] of this day
 * @param stroke [DatePickerStroke] of this day
 * @param shape [Shape] of this day
 */
@Composable
internal fun Day(
    day: DayOfMonth,
    onDayClick: (LocalDate) -> Unit,
    shouldBeDisplayed: Boolean,
    textColor: Color,
    backgroundColor: Color,
    stroke: DatePickerStroke?,
    shape: Shape,
) {
    if (!shouldBeDisplayed) {
        Box(
            modifier = Modifier.size(40.dp),
        )
    } else {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(shape)
                .withNotNull(stroke) {
                    border(it.thickness, it.color, shape)
                }
                .background(backgroundColor)
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

/**
 * Displays the day of the previous month.
 *
 * @param day a day to display
 * @param onDayClick called when [day] is clicked
 * @param shouldBeDisplayed whether or not [day] should be displayed
 */
@Composable
internal fun PreviousMonthDay(day: DayOfMonth, onDayClick: (LocalDate) -> Unit, shouldBeDisplayed: Boolean) {
    Day(
        day = day,
        onDayClick = onDayClick,
        shouldBeDisplayed = shouldBeDisplayed,
        textColor = LocalDatePickerColors.current.previousMonthDayLabelTextColor,
        backgroundColor = LocalDatePickerColors.current.previousMonthDayLabelBackgroundColor,
        stroke = LocalDatePickerColors.current.previousMonthDayLabelStroke,
        shape = LocalDatePickerShapes.current.previousMonthDay,
    )
}

/**
 * Displays the day of the next month.
 *
 * @param day a day to display
 * @param onDayClick called when [day] is clicked
 * @param shouldBeDisplayed whether or not [day] should be displayed
 */
@Composable
internal fun NextMonthDay(day: DayOfMonth, onDayClick: (LocalDate) -> Unit, shouldBeDisplayed: Boolean) {
    Day(
        day = day,
        onDayClick = onDayClick,
        shouldBeDisplayed = shouldBeDisplayed,
        textColor = LocalDatePickerColors.current.nextMonthDayLabelTextColor,
        backgroundColor = LocalDatePickerColors.current.nextMonthDayLabelBackgroundColor,
        stroke = LocalDatePickerColors.current.previousMonthDayLabelStroke,
        shape = LocalDatePickerShapes.current.nextMonthDay,
    )
}

/**
 * Displays the day of the current month.
 *
 * @param day a day to display
 * @param onDayClick called when [day] is clicked
 * @param isToday whether or not [day] is today
 * @param highlightToday whether to highlight today
 * @param isSelected whether or not this [day] is selected
 * @param isInSelectionRange whether or not this [day] is in a selection range
 */
@Composable
internal fun CurrentMonthDay(
    day: DayOfMonth,
    onDayClick: (LocalDate) -> Unit,
    isToday: Boolean,
    highlightToday: Boolean,
    isSelected: Boolean,
    isInSelectionRange: Boolean,
) {
    val shape = when {
        isSelected -> LocalDatePickerShapes.current.currentMonthDaySelected
        isToday && highlightToday -> LocalDatePickerShapes.current.currentMonthDayToday
        else -> LocalDatePickerShapes.current.currentMonthDayUnselected
    }
    // By priority: Selected > In Range > Today > Unselected
    val textColor = when {
        isSelected -> LocalDatePickerColors.current.monthDayLabelSelectedTextColor
        isInSelectionRange -> LocalDatePickerColors.current.monthDayInRangeLabelTextColor
        isToday && highlightToday -> LocalDatePickerColors.current.todayLabelTextColor
        else -> LocalDatePickerColors.current.monthDayLabelUnselectedTextColor
    }

    val backgroundColor = when {
        isSelected -> LocalDatePickerColors.current.monthDayLabelSelectedBackgroundColor
        isToday && highlightToday -> LocalDatePickerColors.current.todayLabelBackgroundColor
        else -> LocalDatePickerColors.current.monthDayLabelUnselectedBackgroundColor
    }

    val stroke: DatePickerStroke? = when {
        isSelected -> LocalDatePickerColors.current.monthDayLabelSelectedStroke
        isToday && highlightToday -> LocalDatePickerColors.current.todayStroke
        else -> LocalDatePickerColors.current.monthDayLabelUnselectedStroke
    }

    Day(
        day = day,
        onDayClick = onDayClick,
        shouldBeDisplayed = true,
        textColor = textColor,
        backgroundColor = backgroundColor,
        stroke = stroke,
        shape = shape,
    )
}