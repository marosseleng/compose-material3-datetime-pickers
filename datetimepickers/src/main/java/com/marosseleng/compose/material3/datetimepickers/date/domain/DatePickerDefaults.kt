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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

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
            previousMonthDayLabelBackgroundColor = Color.Transparent,
            previousMonthDayLabelStroke = null,
            nextMonthDayLabelTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            nextMonthDayLabelBackgroundColor = Color.Transparent,
            nextMonthDayLabelStroke = null,
            monthDayLabelUnselectedTextColor = MaterialTheme.colorScheme.onSurface,
            monthDayLabelUnselectedBackgroundColor = Color.Transparent,
            monthDayLabelUnselectedStroke = null,
            monthDayLabelSelectedTextColor = MaterialTheme.colorScheme.onPrimary,
            monthDayLabelSelectedBackgroundColor = MaterialTheme.colorScheme.primary,
            monthDayLabelSelectedStroke = null,
            monthDayInRangeLabelTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            monthDayInRangeBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
            todayLabelTextColor = MaterialTheme.colorScheme.primary,
            todayLabelBackgroundColor = Color.Transparent,
            todayStroke = DatePickerStroke(Dp.Hairline, MaterialTheme.colorScheme.primary),
        )

    public val shapes: DatePickerShapes
        @Composable
        @ReadOnlyComposable
        get() = DefaultDatePickerShapes(
            previousMonthDay = null,
            currentMonthDaySelected = CircleShape,
            currentMonthDayToday = CircleShape,
            currentMonthDayUnselected = null,
            nextMonthDay = null,
        )

    /**
     * Default typography definitions.
     */
    public val typography: DatePickerTypography
        @Composable
        @ReadOnlyComposable
        get() = DefaultDatePickerTypography(
            weekDay = MaterialTheme.typography.bodySmall,
            day = MaterialTheme.typography.bodySmall,
        )
}