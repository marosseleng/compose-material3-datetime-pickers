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

public interface DatePickerColors {

    public val weekDayLabelTextColor: Color
    public val previousMonthDayLabelTextColor: Color
    public val previousMonthDayLabelBackgroundColor: Color
    public val previousMonthDayLabelStroke: DatePickerStroke?
    public val nextMonthDayLabelTextColor: Color
    public val nextMonthDayLabelBackgroundColor: Color
    public val nextMonthDayLabelStroke: DatePickerStroke?
    public val monthDayLabelUnselectedTextColor: Color
    public val monthDayLabelUnselectedBackgroundColor: Color
    public val monthDayLabelUnselectedStroke: DatePickerStroke?
    public val monthDayLabelSelectedTextColor: Color
    public val monthDayLabelSelectedBackgroundColor: Color
    public val monthDayLabelSelectedStroke: DatePickerStroke?
    public val monthDayInRangeLabelTextColor: Color
    public val monthDayInRangeBackgroundColor: Color
    public val todayLabelTextColor: Color
    public val todayLabelBackgroundColor: Color
    public val todayStroke: DatePickerStroke
    public val unselectedYearTextColor: Color
    public val unselectedYearBackgroundColor: Color
    public val unselectedYearStroke: DatePickerStroke?
    public val selectedYearTextColor: Color
    public val selectedYearBackgroundColor: Color
    public val selectedYearStroke: DatePickerStroke?
    public val unselectedMonthTextColor: Color
    public val unselectedMonthBackgroundColor: Color
    public val unselectedMonthStroke: DatePickerStroke?
    public val selectedMonthTextColor: Color
    public val selectedMonthBackgroundColor: Color
    public val selectedMonthStroke: DatePickerStroke?
}

public data class DefaultDatePickerColors(
    override val weekDayLabelTextColor: Color,
    override val previousMonthDayLabelTextColor: Color,
    override val previousMonthDayLabelBackgroundColor: Color,
    override val previousMonthDayLabelStroke: DatePickerStroke?,
    override val nextMonthDayLabelTextColor: Color,
    override val nextMonthDayLabelBackgroundColor: Color,
    override val nextMonthDayLabelStroke: DatePickerStroke?,
    override val monthDayLabelUnselectedTextColor: Color,
    override val monthDayLabelUnselectedBackgroundColor: Color,
    override val monthDayLabelUnselectedStroke: DatePickerStroke?,
    override val monthDayLabelSelectedTextColor: Color,
    override val monthDayLabelSelectedBackgroundColor: Color,
    override val monthDayLabelSelectedStroke: DatePickerStroke?,
    override val monthDayInRangeLabelTextColor: Color,
    override val monthDayInRangeBackgroundColor: Color,
    override val todayLabelTextColor: Color,
    override val todayLabelBackgroundColor: Color,
    override val todayStroke: DatePickerStroke,
    override val unselectedYearTextColor: Color,
    override val unselectedYearBackgroundColor: Color,
    override val unselectedYearStroke: DatePickerStroke?,
    override val selectedYearTextColor: Color,
    override val selectedYearBackgroundColor: Color,
    override val selectedYearStroke: DatePickerStroke?,
    override val unselectedMonthTextColor: Color,
    override val unselectedMonthBackgroundColor: Color,
    override val unselectedMonthStroke: DatePickerStroke?,
    override val selectedMonthTextColor: Color,
    override val selectedMonthBackgroundColor: Color,
    override val selectedMonthStroke: DatePickerStroke?,
) : DatePickerColors

@Immutable
public data class DatePickerStroke(
    public val thickness:Dp,
    public val color: Color,
)

internal val LocalDatePickerColors: ProvidableCompositionLocal<DatePickerColors> = compositionLocalOf {
    DefaultDatePickerColors(
        weekDayLabelTextColor = Color(0),
        previousMonthDayLabelTextColor = Color(0),
        previousMonthDayLabelBackgroundColor = Color(0),
        previousMonthDayLabelStroke = null,
        nextMonthDayLabelTextColor = Color(0),
        nextMonthDayLabelBackgroundColor = Color(0),
        nextMonthDayLabelStroke = null,
        monthDayLabelUnselectedTextColor = Color(0),
        monthDayLabelUnselectedBackgroundColor = Color(0),
        monthDayLabelUnselectedStroke = null,
        monthDayLabelSelectedTextColor = Color(0),
        monthDayLabelSelectedBackgroundColor = Color(0),
        monthDayLabelSelectedStroke = null,
        monthDayInRangeLabelTextColor = Color(0),
        monthDayInRangeBackgroundColor = Color(0),
        todayLabelTextColor = Color(0),
        todayLabelBackgroundColor = Color(0),
        todayStroke = DatePickerStroke(0.dp, Color(0)),
        unselectedYearTextColor = Color(0),
        unselectedYearBackgroundColor = Color(0),
        unselectedYearStroke = null,
        selectedYearTextColor = Color(0),
        selectedYearBackgroundColor = Color(0),
        selectedYearStroke = null,
        unselectedMonthTextColor = Color(0),
        unselectedMonthBackgroundColor = Color(0),
        unselectedMonthStroke = null,
        selectedMonthTextColor = Color(0),
        selectedMonthBackgroundColor = Color(0),
        selectedMonthStroke = null,
    )
}