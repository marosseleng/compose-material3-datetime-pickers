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

internal val LocalTimePickerShapes: ProvidableCompositionLocal<TimePickerShapes> = compositionLocalOf {
    object : TimePickerShapes {
        override val clockDigitsShape: Shape = CutCornerShape(4.dp)
        override val amPmSwitchShape: Shape = CutCornerShape(4.dp)
    }
}