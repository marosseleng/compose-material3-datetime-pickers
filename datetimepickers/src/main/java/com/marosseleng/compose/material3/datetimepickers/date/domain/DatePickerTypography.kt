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
import androidx.compose.ui.text.TextStyle

/**
 * Interface describing the typography used in DatePicker.
 */
public interface DatePickerTypography {
    /**
     * Text style of a single-day selection dialog's title. Usually the text "Select date"
     */
    public val dialogSingleSelectionTitle: TextStyle

    /**
     * Text style of a single-day selection dialog's headline - description of the currently selected date.
     */
    public val headlineSingleSelection: TextStyle

    /**
     * Text style of a weekday abbreviation.
     */
    public val weekDay: TextStyle

    /**
     * Text style of a common day.
     */
    public val day: TextStyle

    /**
     * Text style of a month-year dropdown.
     */
    public val monthYear: TextStyle

    /**
     * Text style of a year in the year selection.
     */
    public val year: TextStyle

    /**
     * Text style of a month in the month selection.
     */
    public val month: TextStyle
}

internal val LocalDatePickerTypography: ProvidableCompositionLocal<DatePickerTypography> = compositionLocalOf {
    object : DatePickerTypography {
        override val dialogSingleSelectionTitle: TextStyle = TextStyle()
        override val headlineSingleSelection: TextStyle = TextStyle()
        override val weekDay: TextStyle = TextStyle()
        override val day: TextStyle = TextStyle()
        override val monthYear: TextStyle = TextStyle()
        override val year: TextStyle = TextStyle()
        override val month: TextStyle = TextStyle()
    }
}