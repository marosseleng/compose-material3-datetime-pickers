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

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

/**
 * Object holding default values for DatePicker.
 */
public object DatePickerDefaults {
    /**
     * Default colors definitions.
     */
    @Composable
    @ReadOnlyComposable
    public fun colors(
        weekDayLabelTextColor: Color = MaterialTheme.colorScheme.onSurface,
        previousMonthDayLabelTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        previousMonthDayLabelBackgroundColor: Color = Color.Unspecified,
        previousMonthDayLabelStroke: DatePickerStroke? = null,
        nextMonthDayLabelTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        nextMonthDayLabelBackgroundColor: Color = Color.Unspecified,
        nextMonthDayLabelStroke: DatePickerStroke? = null,
        monthDayLabelUnselectedTextColor: Color = MaterialTheme.colorScheme.onSurface,
        monthDayLabelUnselectedBackgroundColor: Color = Color.Unspecified,
        monthDayLabelUnselectedStroke: DatePickerStroke? = null,
        monthDayLabelSelectedTextColor: Color = MaterialTheme.colorScheme.onPrimary,
        monthDayLabelSelectedBackgroundColor: Color = MaterialTheme.colorScheme.primary,
        monthDayLabelSelectedStroke: DatePickerStroke? = null,
        monthDayInRangeLabelTextColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        monthDayInRangeBackgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
        todayLabelTextColor: Color = MaterialTheme.colorScheme.primary,
        todayLabelBackgroundColor: Color = Color.Unspecified,
        todayStroke: DatePickerStroke = DatePickerStroke(1.dp, MaterialTheme.colorScheme.primary),
        yearMonthTextColor: Color = MaterialTheme.colorScheme.onSurface,
        previousNextMonthIconColor: Color = MaterialTheme.colorScheme.onSurface,
        unselectedYearTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        unselectedYearBackgroundColor: Color = Color.Unspecified,
        unselectedYearStroke: DatePickerStroke? = null,
        selectedYearTextColor: Color = MaterialTheme.colorScheme.onPrimary,
        selectedYearBackgroundColor: Color = MaterialTheme.colorScheme.primary,
        selectedYearStroke: DatePickerStroke? = null,
        unselectedMonthTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        unselectedMonthBackgroundColor: Color = Color.Unspecified,
        unselectedMonthStroke: DatePickerStroke? = null,
        selectedMonthTextColor: Color = MaterialTheme.colorScheme.onPrimary,
        selectedMonthBackgroundColor: Color = MaterialTheme.colorScheme.primary,
        selectedMonthStroke: DatePickerStroke? = null,
    ): DatePickerColors = object : DatePickerColors {
        override val weekDayLabelTextColor = weekDayLabelTextColor
        override val previousMonthDayLabelTextColor = previousMonthDayLabelTextColor
        override val previousMonthDayLabelBackgroundColor = previousMonthDayLabelBackgroundColor
        override val previousMonthDayLabelStroke = previousMonthDayLabelStroke
        override val nextMonthDayLabelTextColor = nextMonthDayLabelTextColor
        override val nextMonthDayLabelBackgroundColor = nextMonthDayLabelBackgroundColor
        override val nextMonthDayLabelStroke = nextMonthDayLabelStroke
        override val monthDayLabelUnselectedTextColor = monthDayLabelUnselectedTextColor
        override val monthDayLabelUnselectedBackgroundColor = monthDayLabelUnselectedBackgroundColor
        override val monthDayLabelUnselectedStroke = monthDayLabelUnselectedStroke
        override val monthDayLabelSelectedTextColor = monthDayLabelSelectedTextColor
        override val monthDayLabelSelectedBackgroundColor = monthDayLabelSelectedBackgroundColor
        override val monthDayLabelSelectedStroke = monthDayLabelSelectedStroke
        override val monthDayInRangeLabelTextColor = monthDayInRangeLabelTextColor
        override val monthDayInRangeBackgroundColor = monthDayInRangeBackgroundColor
        override val todayLabelTextColor = todayLabelTextColor
        override val todayLabelBackgroundColor = todayLabelBackgroundColor
        override val todayStroke = todayStroke
        override val yearMonthTextColor = yearMonthTextColor
        override val previousNextMonthIconColor = previousNextMonthIconColor
        override val unselectedYearTextColor = unselectedYearTextColor
        override val unselectedYearBackgroundColor = unselectedYearBackgroundColor
        override val unselectedYearStroke = unselectedYearStroke
        override val selectedYearTextColor = selectedYearTextColor
        override val selectedYearBackgroundColor = selectedYearBackgroundColor
        override val selectedYearStroke = selectedYearStroke
        override val unselectedMonthTextColor = unselectedMonthTextColor
        override val unselectedMonthBackgroundColor = unselectedMonthBackgroundColor
        override val unselectedMonthStroke = unselectedMonthStroke
        override val selectedMonthTextColor = selectedMonthTextColor
        override val selectedMonthBackgroundColor = selectedMonthBackgroundColor
        override val selectedMonthStroke = selectedMonthStroke
    }

    /**
     * Default shapes definitions.
     */
    @Composable
    @ReadOnlyComposable
    public fun shapes(
        previousMonthDay: Shape = CircleShape,
        currentMonthDaySelected: Shape = CircleShape,
        currentMonthDayToday: Shape = CircleShape,
        currentMonthDayUnselected: Shape = CircleShape,
        nextMonthDay: Shape = CircleShape,
        year: Shape = RoundedCornerShape(percent = 50),
        month: Shape = RoundedCornerShape(percent = 50),
    ): DatePickerShapes = object : DatePickerShapes {
        override val previousMonthDay = previousMonthDay
        override val currentMonthDaySelected = currentMonthDaySelected
        override val currentMonthDayToday = currentMonthDayToday
        override val currentMonthDayUnselected = currentMonthDayUnselected
        override val nextMonthDay = nextMonthDay
        override val year = year
        override val month = month
    }

    /**
     * Default typography definitions.
     */
    @Composable
    @ReadOnlyComposable
    public fun typography(
        dialogSingleSelectionTitle: TextStyle = MaterialTheme.typography.labelMedium,
        headlineSingleSelection: TextStyle = MaterialTheme.typography.headlineLarge,
        weekDay: TextStyle = MaterialTheme.typography.bodySmall,
        day: TextStyle = MaterialTheme.typography.bodySmall,
        monthYear: TextStyle = MaterialTheme.typography.labelLarge,
        year: TextStyle = MaterialTheme.typography.bodyLarge,
        month: TextStyle = MaterialTheme.typography.bodyLarge,
    ): DatePickerTypography = object : DatePickerTypography {
        override val dialogSingleSelectionTitle = dialogSingleSelectionTitle
        override val headlineSingleSelection = headlineSingleSelection
        override val weekDay = weekDay
        override val day = day
        override val monthYear = monthYear
        override val year = year
        override val month = month
    }
}