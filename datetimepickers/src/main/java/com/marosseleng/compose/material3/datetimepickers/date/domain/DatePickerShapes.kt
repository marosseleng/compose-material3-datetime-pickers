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

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Interface describing shapes used in DatePicker.
 */
public interface DatePickerShapes {
    /**
     * Shape of previous month's day.
     */
    public val previousMonthDay: Shape

    /**
     * Shape of the current month's selected day.
     */
    public val currentMonthDaySelected: Shape

    /**
     * Shape of today.
     */
    public val currentMonthDayToday: Shape

    /**
     * Shape of the current month's unselected day.
     */
    public val currentMonthDayUnselected: Shape

    /**
     * Shape of the next month's day.
     */
    public val nextMonthDay: Shape

    /**
     * Shape of a year in the year selection.
     */
    public val year: Shape

    /**
     * Shape of a month in the month selection.
     */
    public val month: Shape
}

internal val LocalDatePickerShapes: ProvidableCompositionLocal<DatePickerShapes> = compositionLocalOf {
    object : DatePickerShapes {
        override val previousMonthDay: Shape = CutCornerShape(4.dp)
        override val currentMonthDaySelected: Shape = CutCornerShape(4.dp)
        override val currentMonthDayToday: Shape = CutCornerShape(4.dp)
        override val currentMonthDayUnselected: Shape = CutCornerShape(4.dp)
        override val nextMonthDay: Shape = CutCornerShape(4.dp)
        override val year: Shape = CutCornerShape(4.dp)
        override val month: Shape = CutCornerShape(4.dp)
    }
}