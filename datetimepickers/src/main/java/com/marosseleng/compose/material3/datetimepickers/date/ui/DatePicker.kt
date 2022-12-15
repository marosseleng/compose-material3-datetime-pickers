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

import android.R
import androidx.compose.animation.Crossfade
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.marosseleng.compose.material3.datetimepickers.common.domain.getDisplayName
import com.marosseleng.compose.material3.datetimepickers.common.domain.withNotNull
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerColors
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerDefaults
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerShapes
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerStroke
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerTypography
import com.marosseleng.compose.material3.datetimepickers.date.domain.LocalDatePickerColors
import com.marosseleng.compose.material3.datetimepickers.date.domain.LocalDatePickerShapes
import com.marosseleng.compose.material3.datetimepickers.date.domain.LocalDatePickerTypography
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale
import kotlin.math.ceil

internal enum class MonthYearSelectionMode {
    NO_SELECTION,
    MONTH_SELECTION,
    YEAR_SELECTION,
}

@ExperimentalComposeUiApi
@Composable
public fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateChange: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    initialDate: LocalDate? = null,
    locale: Locale = Locale.getDefault(),
    today: LocalDate = LocalDate.now(),
    showDaysAbbreviations: Boolean = true,
    highlightToday: Boolean = true,
    colors: DatePickerColors = DatePickerDefaults.colors,
    shapes: DatePickerShapes = DatePickerDefaults.shapes,
    typography: DatePickerTypography = DatePickerDefaults.typography,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    shape: Shape = AlertDialogDefaults.shape,
    containerColor: Color = AlertDialogDefaults.containerColor,
    iconContentColor: Color = AlertDialogDefaults.iconContentColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    textContentColor: Color = AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
) {
    var date: LocalDate? by rememberSaveable(initialDate) {
        mutableStateOf(initialDate)
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = { date?.also(onDateChange) }) {
                Text(stringResource(id = R.string.ok))
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(id = R.string.cancel))
            }
        },
        icon = icon,
        title = title,
        text = {
            ModalDatePicker(
                selectedDate = date,
                onDayClick = { date = it },
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
        iconContentColor = iconContentColor,
        titleContentColor = titleContentColor,
        textContentColor = textContentColor,
        tonalElevation = tonalElevation,
        properties = properties,
    )
}

@Composable
internal fun ModalDatePicker(
    selectedDate: LocalDate?,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    locale: Locale = Locale.getDefault(),
    today: LocalDate = LocalDate.now(),
    showDaysAbbreviations: Boolean = true,
    highlightToday: Boolean = true,
    colors: DatePickerColors = DatePickerDefaults.colors,
    shapes: DatePickerShapes = DatePickerDefaults.shapes,
    typography: DatePickerTypography = DatePickerDefaults.typography,
) {
    var yearMonth by remember(selectedDate ?: today) {
        mutableStateOf(YearMonth.of(selectedDate?.year ?: today.year, selectedDate?.month ?: today.month))
    }
    var mode: MonthYearSelectionMode by remember {
        mutableStateOf(MonthYearSelectionMode.NO_SELECTION)
    }

    CompositionLocalProvider(
        LocalDatePickerColors provides colors,
        LocalDatePickerShapes provides shapes,
        LocalDatePickerTypography provides typography,
    ) {

        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .widthIn(max = 280.dp)
        ) {
            DatePickerMonthYearSelection(
                yearMonth = yearMonth,
                mode = mode,
                onPreviousMonthClick = { yearMonth = yearMonth.minusMonths(1L) },
                onMonthClick = {
                    mode = if (mode == MonthYearSelectionMode.NO_SELECTION) {
                        MonthYearSelectionMode.YEAR_SELECTION
                    } else {
                        MonthYearSelectionMode.NO_SELECTION
                    }
                },
                onNextMonthClick = { yearMonth = yearMonth.plusMonths(1L) })

            Crossfade(
                modifier = Modifier
                    .heightIn(max = 280.dp),
                targetState = mode,
            ) { state ->
                when (state) {
                    MonthYearSelectionMode.NO_SELECTION -> {
                        Month(
                            modifier = Modifier.padding(top = 16.dp),
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
                    MonthYearSelectionMode.YEAR_SELECTION -> {
                        YearSelection(
                            modifier = Modifier,
                            selectedYear = yearMonth.year,
                            onYearClick = {
                                yearMonth = YearMonth.of(it, yearMonth.month)
                                mode = MonthYearSelectionMode.MONTH_SELECTION
                            },
                        )
                    }
                    MonthYearSelectionMode.MONTH_SELECTION -> {
                        MonthSelection(
                            modifier = Modifier,
                            locale = Locale.getDefault(),
                            selectedMonth = yearMonth.month,
                            onMonthClick = {
                                yearMonth = YearMonth.of(yearMonth.year, it)
                                // TODO: uncomment
//                                mode = MonthYearSelectionMode.NO_SELECTION
                            }
                        )
                    }
                }
            }
        }
    }
}


/**
 * Handler to make any lazy column (or lazy row) infinite. Will notify the [onLoadMore]
 * callback once needed
 * @param listState state of the list that needs to also be passed to the LazyColumn composable.
 * Get it by calling rememberLazyListState()
 * @param buffer the number of items before the end of the list to call the onLoadMore callback
 * @param onLoadMore will notify when we need to load more
 */
@Composable
internal fun InfiniteListHandler(
    listState: LazyListState,
    buffer: Int = 2,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItemsNumber - buffer)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect {
                onLoadMore()
            }
    }
}

@Composable
internal fun MonthSelection(selectedMonth: Month, onMonthClick: (Month) -> Unit, modifier: Modifier = Modifier, locale: Locale = Locale.getDefault()) {
    val months by remember(Unit) {
        mutableStateOf(Month.values().map { it.getDisplayName(TextStyle.FULL_STANDALONE, locale) })
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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

@Composable
internal fun YearSelection(selectedYear: Int, onYearClick: (Int) -> Unit, modifier: Modifier = Modifier,) {
    val lazyListState = rememberLazyListState()

    var initialLess by remember {
        mutableStateOf(-1)
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

    val buffer = 2

    InfiniteListHandler(listState = lazyListState, buffer = buffer) {
        initialMore += initialMore + buffer
    }
}

/**
 * Current year will be at coordinates [1;1].
 *
 * @param yearsPerRow How many years to get per row.
 * @param rowsLess How many rows to load before the current row's list.
 * @param rowsMore How many rows to load after the current row's list.
 * @param currentYear The current year.
 */
private fun getYears(
    yearsPerRow: Int,
    rowsLess: Int,
    rowsMore: Int,
    currentYear: Int = Year.now().value,
): List<List<Int>> {
    val result = mutableListOf<List<Int>>()

    val startOfTheRow = (currentYear - 1)

    for (row in (rowsLess..rowsMore)) {
        val rowBeginning = startOfTheRow + (row * yearsPerRow)
        val currentRow = mutableListOf<Int>()
        (0 until yearsPerRow).forEach {
            currentRow.add(rowBeginning + it)
        }
        result.add(currentRow)
    }

    return result
}

@Composable
internal fun DatePickerMonthYearSelection(
    yearMonth: YearMonth,
    mode: MonthYearSelectionMode,
    onPreviousMonthClick: () -> Unit,
    onMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    modifier: Modifier = Modifier,
    locale: Locale = Locale.getDefault()
) {

    val iconRotation by animateFloatAsState(targetValue = if (mode == MonthYearSelectionMode.NO_SELECTION) 0F else 180f)

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
                modifier = Modifier.padding(start = 8.dp),
                text = yearMonth.getDisplayName(locale),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Icon(
                modifier = Modifier.rotate(iconRotation),
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Select month",
                tint = MaterialTheme.colorScheme.onSurface,
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
                    contentDescription = "Previous month",
                    tint = MaterialTheme.colorScheme.onSurface,
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
                    contentDescription = "Next month",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
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
                    highlightToday = highlightToday,
                    selectionFrom = selectionFrom,
                    selectionTo = selectionTo,
                )
            }
        }
    }
}

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

@Composable
internal fun CurrentMonthDay(
    day: DayOfMonth,
    onDayClick: (LocalDate) -> Unit,
    highlightToday: Boolean,
    selectionFrom: LocalDate?,
    selectionTo: LocalDate?
) {
    val shape = when {
        day.actualDay == selectionFrom -> LocalDatePickerShapes.current.currentMonthDaySelected
        day.actualDay == selectionTo -> LocalDatePickerShapes.current.currentMonthDaySelected
        day.isToday && highlightToday -> LocalDatePickerShapes.current.currentMonthDayToday
        else -> LocalDatePickerShapes.current.currentMonthDayUnselected
    }
    // By priority: Selected > In Range > Today > Unselected
    val textColor = when {
        day.actualDay == selectionFrom -> LocalDatePickerColors.current.monthDayLabelSelectedTextColor
        day.actualDay == selectionTo -> LocalDatePickerColors.current.monthDayLabelSelectedTextColor
        selectionFrom != null && selectionTo != null && day.actualDay > selectionFrom && day.actualDay < selectionTo ->
            LocalDatePickerColors.current.monthDayInRangeLabelTextColor
        day.isToday && highlightToday -> LocalDatePickerColors.current.todayLabelTextColor
        else -> LocalDatePickerColors.current.monthDayLabelUnselectedTextColor
    }

    val backgroundColor = when {
        day.actualDay == selectionFrom -> LocalDatePickerColors.current.monthDayLabelSelectedBackgroundColor
        day.actualDay == selectionTo -> LocalDatePickerColors.current.monthDayLabelSelectedBackgroundColor
        selectionFrom != null && selectionTo != null && day.actualDay > selectionFrom && day.actualDay < selectionTo ->
            LocalDatePickerColors.current.monthDayInRangeBackgroundColor
        day.isToday && highlightToday -> LocalDatePickerColors.current.todayLabelBackgroundColor
        else -> LocalDatePickerColors.current.monthDayLabelUnselectedBackgroundColor
    }

    val stroke: DatePickerStroke? = when {
        day.actualDay == selectionFrom -> LocalDatePickerColors.current.monthDayLabelSelectedStroke
        day.actualDay == selectionTo -> LocalDatePickerColors.current.monthDayLabelSelectedStroke
        day.isToday && highlightToday -> LocalDatePickerColors.current.todayStroke
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

/**
 * Displays the grid of days within a given [month]. Also displays the days abbreviation if enabled.
 */
@Composable
internal fun Month(
    month: YearMonth,
    locale: Locale,
    onDayClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
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