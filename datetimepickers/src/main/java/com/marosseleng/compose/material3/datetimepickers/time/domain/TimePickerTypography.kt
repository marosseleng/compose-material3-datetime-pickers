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

package com.marosseleng.compose.material3.datetimepickers.time.domain;

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle

/**
 * Interface describing text styles used in TimePicker.
 */
public interface TimePickerTypography {

    /**
     * Text style of "hour" digit box.
     */
    public val digitsHour: TextStyle

    /**
     * Text style of the ':' character between digit boxes.
     */
    public val digitsColon: TextStyle

    /**
     * Text style of "minute" digit box.
     */
    public val digitsMinute: TextStyle

    /**
     * Text style of the "AM" text in the AM/PM box.
     */
    public val am: TextStyle

    /**
     * Text style of the "PM" text in the AM/PM box.
     */
    public val pm: TextStyle

    /**
     * Text style of the numbers in the clock dial.
     */
    public val dialNumber: TextStyle
}

internal data class DefaultTimePickerTypography(
    override val digitsHour: TextStyle,
    override val digitsColon: TextStyle,
    override val digitsMinute: TextStyle,
    override val am: TextStyle,
    override val pm: TextStyle,
    override val dialNumber: TextStyle,
) : TimePickerTypography

internal val LocalTimePickerTypography: ProvidableCompositionLocal<TimePickerTypography> = compositionLocalOf {
    DefaultTimePickerTypography(
        digitsHour = TextStyle(),
        digitsColon = TextStyle(),
        digitsMinute = TextStyle(),
        am = TextStyle(),
        pm = TextStyle(),
        dialNumber = TextStyle(),
    )
}