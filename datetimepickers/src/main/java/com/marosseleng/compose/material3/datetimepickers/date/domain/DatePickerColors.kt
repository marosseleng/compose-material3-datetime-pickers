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

package com.marosseleng.compose.material3.datetimepickers.date.domain

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Interface describing colors used in DatePicker.
 */
public interface DatePickerColors {

    /**
     * Text color of the single-date selection dialog. Usually text "select date"
     */
    public val dialogSingleSelectionTitleTextColor: Color

    /**
     * Text color of the selected date in the dialog.
     */
    public val headlineSingleSelectionTextColor: Color

    /**
     * Text color of a weekday abbreviation.
     */
    public val weekDayLabelTextColor: Color

    /**
     * Text color of a previous month's day.
     */
    public val previousMonthDayLabelTextColor: Color

    /**
     * Background color of a previous month's day.
     */
    public val previousMonthDayLabelBackgroundColor: Color

    /**
     * Stroke of a previous month's day.
     */
    public val previousMonthDayLabelStroke: DatePickerStroke?

    /**
     * Text color of a next month's day.
     */
    public val nextMonthDayLabelTextColor: Color

    /**
     * Background color of a next month's day.
     */
    public val nextMonthDayLabelBackgroundColor: Color

    /**
     * Stroke of a next month's day.
     */
    public val nextMonthDayLabelStroke: DatePickerStroke?

    /**
     * Text color of an unselected day.
     */
    public val monthDayLabelUnselectedTextColor: Color

    /**
     * Background color of an unselected day.
     */
    public val monthDayLabelUnselectedBackgroundColor: Color

    /**
     * Stroke of an unselected day.
     */
    public val monthDayLabelUnselectedStroke: DatePickerStroke?

    /**
     * Text color of a selected day.
     */
    public val monthDayLabelSelectedTextColor: Color

    /**
     * Background color of a selected day.
     */
    public val monthDayLabelSelectedBackgroundColor: Color

    /**
     * Stroke of a selected day.
     */
    public val monthDayLabelSelectedStroke: DatePickerStroke?

    /**
     * Text color of a day within a selection range, but not the selected one.
     */
    public val monthDayInRangeLabelTextColor: Color

    /**
     * Background color of a day within a selection range, but not the selected one.
     */
    public val monthDayInRangeBackgroundColor: Color

    /**
     * Text color of today.
     */
    public val todayLabelTextColor: Color

    /**
     * Background color of today.
     */
    public val todayLabelBackgroundColor: Color

    /**
     * Stroke of today.
     */
    public val todayStroke: DatePickerStroke

    /**
     * Text color of a year-month dropdown.
     */
    public val yearMonthTextColor: Color

    /**
     * Tint color of the "previous month" and "next month" arrow icons.
     */
    public val previousNextMonthIconColor: Color

    /**
     * Text color of an unselected year in the year selection.
     */
    public val unselectedYearTextColor: Color

    /**
     * Background color of an unselected year in the year selection.
     */
    public val unselectedYearBackgroundColor: Color

    /**
     * Stroke of an unselected tear in the year selection.
     */
    public val unselectedYearStroke: DatePickerStroke?

    /**
     * Text color of a selected year in the year selection.
     */
    public val selectedYearTextColor: Color

    /**
     * Background color of a selected year in the year selection.
     */
    public val selectedYearBackgroundColor: Color

    /**
     * Stroke of a selected year in the year selection.
     */
    public val selectedYearStroke: DatePickerStroke?

    /**
     * Text color of an unselected month in the month selection.
     */
    public val unselectedMonthTextColor: Color

    /**
     * Background color of an unselected month in the month selection.
     */
    public val unselectedMonthBackgroundColor: Color

    /**
     * Stroke of an unselected month in the month selection.
     */
    public val unselectedMonthStroke: DatePickerStroke?

    /**
     * Text color of a selected month in the month selection.
     */
    public val selectedMonthTextColor: Color

    /**
     * Background color of a selected month in the month selection.
     */
    public val selectedMonthBackgroundColor: Color

    /**
     * Stroke of a selected month in the month selection.
     */
    public val selectedMonthStroke: DatePickerStroke?
}

/**
 * Simple structure for describing strokes.
 *
 * @property thickness the thickness of this stroke in [Dp].
 * @property color the color of this stroke.
 */
@Immutable
public data class DatePickerStroke(
    public val thickness: Dp,
    public val color: Color,
)

internal val LocalDatePickerColors: ProvidableCompositionLocal<DatePickerColors> = compositionLocalOf {
    object : DatePickerColors {
        override val dialogSingleSelectionTitleTextColor: Color = Color.Transparent
        override val headlineSingleSelectionTextColor: Color = Color.Transparent
        override val weekDayLabelTextColor: Color = Color.Transparent
        override val previousMonthDayLabelTextColor: Color = Color.Transparent
        override val previousMonthDayLabelBackgroundColor: Color = Color.Transparent
        override val previousMonthDayLabelStroke: DatePickerStroke? = null
        override val nextMonthDayLabelTextColor: Color = Color.Transparent
        override val nextMonthDayLabelBackgroundColor: Color = Color.Transparent
        override val nextMonthDayLabelStroke: DatePickerStroke? = null
        override val monthDayLabelUnselectedTextColor: Color = Color.Transparent
        override val monthDayLabelUnselectedBackgroundColor: Color = Color.Transparent
        override val monthDayLabelUnselectedStroke: DatePickerStroke? = null
        override val monthDayLabelSelectedTextColor: Color = Color.Transparent
        override val monthDayLabelSelectedBackgroundColor: Color = Color.Transparent
        override val monthDayLabelSelectedStroke: DatePickerStroke? = null
        override val monthDayInRangeLabelTextColor: Color = Color.Transparent
        override val monthDayInRangeBackgroundColor: Color = Color.Transparent
        override val todayLabelTextColor: Color = Color.Transparent
        override val todayLabelBackgroundColor: Color = Color.Transparent
        override val todayStroke: DatePickerStroke = DatePickerStroke(thickness = 0.dp, Color.Transparent)
        override val yearMonthTextColor: Color = Color.Transparent
        override val previousNextMonthIconColor: Color = Color.Transparent
        override val unselectedYearTextColor: Color = Color.Transparent
        override val unselectedYearBackgroundColor: Color = Color.Transparent
        override val unselectedYearStroke: DatePickerStroke? = null
        override val selectedYearTextColor: Color = Color.Transparent
        override val selectedYearBackgroundColor: Color = Color.Transparent
        override val selectedYearStroke: DatePickerStroke? = null
        override val unselectedMonthTextColor: Color = Color.Transparent
        override val unselectedMonthBackgroundColor: Color = Color.Transparent
        override val unselectedMonthStroke: DatePickerStroke? = null
        override val selectedMonthTextColor: Color = Color.Transparent
        override val selectedMonthBackgroundColor: Color = Color.Transparent
        override val selectedMonthStroke: DatePickerStroke? = null
    }
}