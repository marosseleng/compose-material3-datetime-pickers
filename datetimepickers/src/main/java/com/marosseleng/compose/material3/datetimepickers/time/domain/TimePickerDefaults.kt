package com.marosseleng.compose.material3.datetimepickers.time.domain

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp

/**
 * Object holding default values for [TimePicker].
 */
public object TimePickerDefaults {

    /**
     * Default colors definitions.
     */
    public val colors: TimePickerColors
        @Composable
        @ReadOnlyComposable
        get() = DefaultTimePickerColors(
            clockDigitsSelectedBackgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.24f),
            clockDigitsSelectedTextColor = MaterialTheme.colorScheme.primary,
            clockDigitsSelectedBorderStroke = null,
            clockDigitsUnselectedBackgroundColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.24f),
            clockDigitsUnselectedTextColor = MaterialTheme.colorScheme.onSurface,
            clockDigitsUnselectedBorderStroke = null,
            clockDigitsColonTextColor = MaterialTheme.colorScheme.onSurface,
            amPmSwitchFieldSelectedBackgroundColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.24f),
            amPmSwitchFieldSelectedTextColor = MaterialTheme.colorScheme.tertiary,
            amPmSwitchFieldUnselectedBackgroundColor = Color.Transparent,
            amPmSwitchFieldUnselectedTextColor = MaterialTheme.colorScheme.onSurface,
            amPmSwitchBorderDividerStroke = TimePickerStroke(Dp.Hairline, MaterialTheme.colorScheme.outline),
            dialCenterColor = MaterialTheme.colorScheme.primary,
            dialHandColor = MaterialTheme.colorScheme.primary,
            dialBackgroundColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.24f),
            dialNumberSelectedBackgroundColor = MaterialTheme.colorScheme.primary,
            dialNumberSelectedTextColor = MaterialTheme.colorScheme.onPrimary,
            dialNumberUnselectedBackgroundColor = Color.Transparent,
            dialNumberUnselectedTextColor = MaterialTheme.colorScheme.onSurface,
        )

    /**
     * Default shapes definitions.
     */
    public val shapes: TimePickerShapes
        @Composable
        @ReadOnlyComposable
        get() = DefaultTimePickerShapes(
            clockDigitsShape = MaterialTheme.shapes.small,
            amPmSwitchShape = MaterialTheme.shapes.small,
        )

    /**
     * Default typography definitions.
     */
    public val typography: TimePickerTypography
        @Composable
        @ReadOnlyComposable
        get() = DefaultTimePickerTypography(
            digitsHour = MaterialTheme.typography.displayMedium.copy(textAlign = TextAlign.Center),
            digitsColon = MaterialTheme.typography.displayMedium.copy(textAlign = TextAlign.Center),
            digitsMinute = MaterialTheme.typography.displayMedium.copy(textAlign = TextAlign.Center),
            am = MaterialTheme.typography.titleMedium,
            pm = MaterialTheme.typography.titleMedium,
            dialNumber = MaterialTheme.typography.titleSmall,
        )
}