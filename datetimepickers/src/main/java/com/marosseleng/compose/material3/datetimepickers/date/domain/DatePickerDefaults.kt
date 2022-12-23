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
import androidx.compose.ui.unit.dp

/**
 * Object holding default values for DatePicker.
 */
public object DatePickerDefaults {
    /**
     * Default colors definitions.
     */
    public val colors: DatePickerColors
        @Composable
        @ReadOnlyComposable
        get() = DefaultDatePickerColors(
            weekDayLabelTextColor = MaterialTheme.colorScheme.onSurface,
            previousMonthDayLabelTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            previousMonthDayLabelBackgroundColor = Color.Unspecified,
            previousMonthDayLabelStroke = null,
            nextMonthDayLabelTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            nextMonthDayLabelBackgroundColor = Color.Unspecified,
            nextMonthDayLabelStroke = null,
            monthDayLabelUnselectedTextColor = MaterialTheme.colorScheme.onSurface,
            monthDayLabelUnselectedBackgroundColor = Color.Unspecified,
            monthDayLabelUnselectedStroke = null,
            monthDayLabelSelectedTextColor = MaterialTheme.colorScheme.onPrimary,
            monthDayLabelSelectedBackgroundColor = MaterialTheme.colorScheme.primary,
            monthDayLabelSelectedStroke = null,
            monthDayInRangeLabelTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            monthDayInRangeBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
            todayLabelTextColor = MaterialTheme.colorScheme.primary,
            todayLabelBackgroundColor = Color.Unspecified,
            todayStroke = DatePickerStroke(1.dp, MaterialTheme.colorScheme.primary),
            yearMonthTextColor = MaterialTheme.colorScheme.onSurface,
            previousNextMonthIconColor = MaterialTheme.colorScheme.onSurface,
            unselectedYearTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedYearBackgroundColor = Color.Unspecified,
            unselectedYearStroke = null,
            selectedYearTextColor = MaterialTheme.colorScheme.onPrimary,
            selectedYearBackgroundColor = MaterialTheme.colorScheme.primary,
            selectedYearStroke = null,
            unselectedMonthTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedMonthBackgroundColor = Color.Unspecified,
            unselectedMonthStroke = null,
            selectedMonthTextColor = MaterialTheme.colorScheme.onPrimary,
            selectedMonthBackgroundColor = MaterialTheme.colorScheme.primary,
            selectedMonthStroke = null,
        )

    /**
     * Default shapes definitions.
     */
    public val shapes: DatePickerShapes
        @Composable
        @ReadOnlyComposable
        get() = DefaultDatePickerShapes(
            previousMonthDay = CircleShape,
            currentMonthDaySelected = CircleShape,
            currentMonthDayToday = CircleShape,
            currentMonthDayUnselected = CircleShape,
            nextMonthDay = CircleShape,
            year = RoundedCornerShape(percent = 50),
            month = RoundedCornerShape(percent = 50),
        )

    /**
     * Default typography definitions.
     */
    public val typography: DatePickerTypography
        @Composable
        @ReadOnlyComposable
        get() = DefaultDatePickerTypography(
            dialogSingleSelectionTitle = MaterialTheme.typography.labelMedium,
            headlineSingleSelection = MaterialTheme.typography.headlineLarge,
            weekDay = MaterialTheme.typography.bodySmall,
            day = MaterialTheme.typography.bodySmall,
            monthYear = MaterialTheme.typography.labelLarge,
            year = MaterialTheme.typography.bodyLarge,
            month = MaterialTheme.typography.bodyLarge,
        )
}