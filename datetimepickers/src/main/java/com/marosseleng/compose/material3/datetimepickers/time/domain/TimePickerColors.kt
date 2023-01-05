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

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Interface describing colors used in TimePicker.
 */
public interface TimePickerColors {

    /**
     * Background color of the selected digit box.
     */
    public val clockDigitsSelectedBackgroundColor: Color

    /**
     * Text color of the selected digit box.
     */
    public val clockDigitsSelectedTextColor: Color

    /**
     * Stroke of the selected digit box.
     */
    public val clockDigitsSelectedBorderStroke: TimePickerStroke?

    /**
     * Background color of the unselected digit box.
     */
    public val clockDigitsUnselectedBackgroundColor: Color

    /**
     * Text color of the unselected digit box.
     */
    public val clockDigitsUnselectedTextColor: Color

    /**
     * Stroke of the selected digit box.
     */
    public val clockDigitsUnselectedBorderStroke: TimePickerStroke?

    /**
     * Text color of the ':' character between digit boxes.
     */
    public val clockDigitsColonTextColor: Color

    /**
     * Background color of the selected AM/PM box.
     */
    public val amPmSwitchFieldSelectedBackgroundColor: Color

    /**
     * Text color of the selected AM/PM box.
     */
    public val amPmSwitchFieldSelectedTextColor: Color

    /**
     * Background color of the unselected AM/PM box.
     */
    public val amPmSwitchFieldUnselectedBackgroundColor: Color

    /**
     * Text color of the unselected AM/PM box.
     */
    public val amPmSwitchFieldUnselectedTextColor: Color

    /**
     * Border and divider stroke of AM/PM box.
     */
    public val amPmSwitchBorderDividerStroke: TimePickerStroke?

    /**
     * Color of the center of the dial.
     */
    public val dialCenterColor: Color

    /**
     * Color of the "clock hand".
     */
    public val dialHandColor: Color

    /**
     * Background color of the dial.
     */
    public val dialBackgroundColor: Color

    /**
     * Background color of the selected dial number.
     */
    public val dialNumberSelectedBackgroundColor: Color

    /**
     * Text color of the selected dial number.
     */
    public val dialNumberSelectedTextColor: Color

    /**
     * Background color of the unselected dial number.
     */
    public val dialNumberUnselectedBackgroundColor: Color

    /**
     * Text color of the unselected dial number.
     */
    public val dialNumberUnselectedTextColor: Color
}

/**
 * Simple structure for describing strokes.
 *
 * @property thickness the thickness of this stroke in [Dp].
 * @property color the color of this stroke.
 */
@Immutable
public data class TimePickerStroke(
    public val thickness: Dp,
    public val color: Color,
)

internal val LocalTimePickerColors: ProvidableCompositionLocal<TimePickerColors> = compositionLocalOf {
    object : TimePickerColors {
        override val clockDigitsSelectedBackgroundColor: Color = Color.Transparent
        override val clockDigitsSelectedTextColor: Color = Color.Transparent
        override val clockDigitsSelectedBorderStroke: TimePickerStroke? = null
        override val clockDigitsUnselectedBackgroundColor: Color = Color.Transparent
        override val clockDigitsUnselectedTextColor: Color = Color.Transparent
        override val clockDigitsUnselectedBorderStroke: TimePickerStroke? = null
        override val clockDigitsColonTextColor: Color = Color.Transparent
        override val amPmSwitchFieldSelectedBackgroundColor: Color = Color.Transparent
        override val amPmSwitchFieldSelectedTextColor: Color = Color.Transparent
        override val amPmSwitchFieldUnselectedBackgroundColor: Color = Color.Transparent
        override val amPmSwitchFieldUnselectedTextColor: Color = Color.Transparent
        override val amPmSwitchBorderDividerStroke: TimePickerStroke? = null
        override val dialCenterColor: Color = Color.Transparent
        override val dialHandColor: Color = Color.Transparent
        override val dialBackgroundColor: Color = Color.Transparent
        override val dialNumberSelectedBackgroundColor: Color = Color.Transparent
        override val dialNumberSelectedTextColor: Color = Color.Transparent
        override val dialNumberUnselectedBackgroundColor: Color = Color.Transparent
        override val dialNumberUnselectedTextColor: Color = Color.Transparent
    }
}