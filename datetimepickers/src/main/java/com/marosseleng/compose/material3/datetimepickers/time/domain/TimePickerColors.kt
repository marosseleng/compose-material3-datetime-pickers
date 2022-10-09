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
    public val thickness:Dp,
    public val color: Color,
)

internal data class DefaultTimePickerColors(
    override val clockDigitsSelectedBackgroundColor: Color,
    override val clockDigitsSelectedTextColor: Color,
    override val clockDigitsSelectedBorderStroke: TimePickerStroke?,
    override val clockDigitsUnselectedBackgroundColor: Color,
    override val clockDigitsUnselectedTextColor: Color,
    override val clockDigitsUnselectedBorderStroke: TimePickerStroke?,
    override val clockDigitsColonTextColor: Color,
    override val amPmSwitchFieldSelectedBackgroundColor: Color,
    override val amPmSwitchFieldSelectedTextColor: Color,
    override val amPmSwitchFieldUnselectedBackgroundColor: Color,
    override val amPmSwitchFieldUnselectedTextColor: Color,
    override val amPmSwitchBorderDividerStroke: TimePickerStroke?,
    override val dialCenterColor: Color,
    override val dialHandColor: Color,
    override val dialBackgroundColor: Color,
    override val dialNumberSelectedBackgroundColor: Color,
    override val dialNumberSelectedTextColor: Color,
    override val dialNumberUnselectedBackgroundColor: Color,
    override val dialNumberUnselectedTextColor: Color,
) : TimePickerColors

internal val LocalTimePickerColors: ProvidableCompositionLocal<TimePickerColors> = compositionLocalOf {
    DefaultTimePickerColors(
        clockDigitsSelectedBackgroundColor = Color.Transparent,
        clockDigitsSelectedTextColor = Color.Transparent,
        clockDigitsSelectedBorderStroke = null,
        clockDigitsUnselectedBackgroundColor = Color.Transparent,
        clockDigitsUnselectedTextColor = Color.Transparent,
        clockDigitsUnselectedBorderStroke = null,
        clockDigitsColonTextColor = Color.Transparent,
        amPmSwitchFieldSelectedBackgroundColor = Color.Transparent,
        amPmSwitchFieldSelectedTextColor = Color.Transparent,
        amPmSwitchFieldUnselectedBackgroundColor = Color.Transparent,
        amPmSwitchFieldUnselectedTextColor = Color.Transparent,
        amPmSwitchBorderDividerStroke = null,
        dialCenterColor = Color.Transparent,
        dialHandColor = Color.Transparent,
        dialBackgroundColor = Color.Transparent,
        dialNumberSelectedBackgroundColor = Color.Transparent,
        dialNumberSelectedTextColor = Color.Transparent,
        dialNumberUnselectedBackgroundColor = Color.Transparent,
        dialNumberUnselectedTextColor = Color.Transparent,
    )
}