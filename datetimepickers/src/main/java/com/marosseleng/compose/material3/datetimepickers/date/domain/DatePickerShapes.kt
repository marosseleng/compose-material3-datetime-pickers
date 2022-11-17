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

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Shape

// By priority: Selected > In Range > Today > Unselected
public interface DatePickerShapes {
    public val previousMonthDay: Shape?
    public val currentMonthDaySelected: Shape?
    public val currentMonthDayToday: Shape?
    public val currentMonthDayUnselected: Shape?
    public val nextMonthDay: Shape?
}

internal data class DefaultDatePickerShapes(
    override val previousMonthDay: Shape?,
    override val currentMonthDaySelected: Shape?,
    override val currentMonthDayToday: Shape?,
    override val currentMonthDayUnselected: Shape?,
    override val nextMonthDay: Shape?,
) : DatePickerShapes

internal val LocalDatePickerShapes: ProvidableCompositionLocal<DatePickerShapes> = compositionLocalOf {
    DefaultDatePickerShapes(
        previousMonthDay = null,
        currentMonthDaySelected = null,
        currentMonthDayToday = null,
        currentMonthDayUnselected = null,
        nextMonthDay = null,
    )
}