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

public interface DatePickerTypography {

    public val weekDay: TextStyle
    public val day: TextStyle
    public val year: TextStyle
    public val month: TextStyle
}

internal data class DefaultDatePickerTypography(
    override val weekDay: TextStyle,
    override val day: TextStyle,
    override val year: TextStyle,
    override val month: TextStyle,
) : DatePickerTypography

internal val LocalDatePickerTypography: ProvidableCompositionLocal<DatePickerTypography> = compositionLocalOf {
    DefaultDatePickerTypography(
        weekDay = TextStyle(),
        day = TextStyle(),
        year = TextStyle(),
        month = TextStyle(),
    )
}