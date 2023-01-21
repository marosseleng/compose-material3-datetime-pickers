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

package com.marosseleng.compose.material3.datetimepickers.time.domain

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp

/**
 * Object holding default values for TimePicker.
 */
public object TimePickerDefaults {

    /**
     * Default colors definitions.
     */
    @Composable
    @ReadOnlyComposable
    public fun colors(
        dialogTitleTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        clockDigitsSelectedBackgroundColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.24f),
        clockDigitsSelectedTextColor: Color = MaterialTheme.colorScheme.primary,
        clockDigitsSelectedBorderStroke: TimePickerStroke? = null,
        clockDigitsUnselectedBackgroundColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.24f),
        clockDigitsUnselectedTextColor: Color = MaterialTheme.colorScheme.onSurface,
        clockDigitsUnselectedBorderStroke: TimePickerStroke? = null,
        clockDigitsColonTextColor: Color = MaterialTheme.colorScheme.onSurface,
        amPmSwitchFieldSelectedBackgroundColor: Color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.24f),
        amPmSwitchFieldSelectedTextColor: Color = MaterialTheme.colorScheme.tertiary,
        amPmSwitchFieldUnselectedBackgroundColor: Color = Color.Transparent,
        amPmSwitchFieldUnselectedTextColor: Color = MaterialTheme.colorScheme.onSurface,
        amPmSwitchBorderDividerStroke: TimePickerStroke? = TimePickerStroke(
            Dp.Hairline,
            MaterialTheme.colorScheme.outline
        ),
        dialCenterColor: Color = MaterialTheme.colorScheme.primary,
        dialHandColor: Color = MaterialTheme.colorScheme.primary,
        dialBackgroundColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.24f),
        dialNumberSelectedBackgroundColor: Color = MaterialTheme.colorScheme.primary,
        dialNumberSelectedTextColor: Color = MaterialTheme.colorScheme.onPrimary,
        dialNumberUnselectedBackgroundColor: Color = Color.Transparent,
        dialNumberUnselectedTextColor: Color = MaterialTheme.colorScheme.onSurface,
    ): TimePickerColors = object : TimePickerColors {
        override val dialogTitleTextColor: Color = dialogTitleTextColor
        override val clockDigitsSelectedBackgroundColor = clockDigitsSelectedBackgroundColor
        override val clockDigitsSelectedTextColor = clockDigitsSelectedTextColor
        override val clockDigitsSelectedBorderStroke = clockDigitsSelectedBorderStroke
        override val clockDigitsUnselectedBackgroundColor = clockDigitsUnselectedBackgroundColor
        override val clockDigitsUnselectedTextColor = clockDigitsUnselectedTextColor
        override val clockDigitsUnselectedBorderStroke = clockDigitsUnselectedBorderStroke
        override val clockDigitsColonTextColor = clockDigitsColonTextColor
        override val amPmSwitchFieldSelectedBackgroundColor = amPmSwitchFieldSelectedBackgroundColor
        override val amPmSwitchFieldSelectedTextColor = amPmSwitchFieldSelectedTextColor
        override val amPmSwitchFieldUnselectedBackgroundColor = amPmSwitchFieldUnselectedBackgroundColor
        override val amPmSwitchFieldUnselectedTextColor = amPmSwitchFieldUnselectedTextColor
        override val amPmSwitchBorderDividerStroke = amPmSwitchBorderDividerStroke
        override val dialCenterColor = dialCenterColor
        override val dialHandColor = dialHandColor
        override val dialBackgroundColor = dialBackgroundColor
        override val dialNumberSelectedBackgroundColor = dialNumberSelectedBackgroundColor
        override val dialNumberSelectedTextColor = dialNumberSelectedTextColor
        override val dialNumberUnselectedBackgroundColor = dialNumberUnselectedBackgroundColor
        override val dialNumberUnselectedTextColor = dialNumberUnselectedTextColor
    }

    /**
     * Default shapes definitions.
     */
    @Composable
    @ReadOnlyComposable
    public fun shapes(
        clockDigitsShape: Shape = MaterialTheme.shapes.small,
        amPmSwitchShape: Shape = MaterialTheme.shapes.small,
    ): TimePickerShapes = object : TimePickerShapes {
        override val clockDigitsShape = clockDigitsShape
        override val amPmSwitchShape = amPmSwitchShape
    }

    /**
     * Default typography definitions.
     */
    @Composable
    @ReadOnlyComposable
    public fun typography(
        dialogTitle: TextStyle = MaterialTheme.typography.labelMedium,
        digitsHour: TextStyle = MaterialTheme.typography.displayLarge.copy(textAlign = TextAlign.Center),
        digitsColon: TextStyle = MaterialTheme.typography.displayLarge.copy(textAlign = TextAlign.Center),
        digitsMinute: TextStyle = MaterialTheme.typography.displayLarge.copy(textAlign = TextAlign.Center),
        am: TextStyle = MaterialTheme.typography.titleMedium,
        pm: TextStyle = MaterialTheme.typography.titleMedium,
        dialNumber: TextStyle = MaterialTheme.typography.titleSmall,
    ): TimePickerTypography = object : TimePickerTypography {
        override val dialogTitle = dialogTitle
        override val digitsHour = digitsHour
        override val digitsColon = digitsColon
        override val digitsMinute = digitsMinute
        override val am = am
        override val pm = pm
        override val dialNumber = dialNumber
    }
}