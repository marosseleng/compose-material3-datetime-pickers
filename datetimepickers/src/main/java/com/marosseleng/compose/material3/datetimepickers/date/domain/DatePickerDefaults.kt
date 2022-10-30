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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

public object DatePickerDefaults {
    /**
     * Default colors definitions.
     */
    public val colors: DatePickerColors
        @Composable
        @ReadOnlyComposable
        get() = DefaultDatePickerColors(
            weekDayLabelTextColor = Color(0),
            previousMonthDayLabelTextColor = Color(0),
            nextMonthDayLabelTextColor = Color(0),
            monthDayLabelUnselectedTextColor = Color(0),
            monthDayLabelUnselectedBackgroundColor = Color(0),
            monthDayLabelUnselectedStroke = DatePickerStroke(0.dp, Color(0)),
            monthDayLabelSelectedTextColor = Color(0),
            monthDayLabelSelectedBackgroundColor = Color(0),
            monthDayLabelSelectedStroke = DatePickerStroke(0.dp, Color(0)),
            todayLabelTextColor = Color(0),
            todayLabelBackgroundColor = Color(0),
            todayStroke = DatePickerStroke(0.dp, Color(0)),
        )
}