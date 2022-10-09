package com.marosseleng.compose.material3.datetimepickers.time.domain;

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Util interface representing the set of [Shape]s used in TimePicker.
 */
public interface TimePickerShapes {

    /**
     * [Shape] of clock digits' fields.
     */
    public val clockDigitsShape: Shape

    /**
     * [Shape] of AM/PM switch.
     */
    public val amPmSwitchShape: Shape
}

internal data class DefaultTimePickerShapes(
    override val clockDigitsShape: Shape,
    override val amPmSwitchShape: Shape,
) : TimePickerShapes

internal val LocalTimePickerShapes: ProvidableCompositionLocal<TimePickerShapes> = compositionLocalOf {
    DefaultTimePickerShapes(
        clockDigitsShape = CutCornerShape(4.dp),
        amPmSwitchShape = CutCornerShape(4.dp),
    )
}