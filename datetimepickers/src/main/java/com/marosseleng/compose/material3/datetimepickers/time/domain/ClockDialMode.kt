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

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * Helper sealed class to differentiate between displayed dials in a crossfade animation.
 */
internal sealed class ClockDialMode : Parcelable {

    /**
     * Represents hours.
     *
     * @property is24HourFormat whether or not this dial represents 24-hour format clock.
     */
    @Parcelize
    internal data class Hours(val is24HourFormat: Boolean) : ClockDialMode() {
        @IgnoredOnParcel
        override val isHours = true
    }

    /**
     * Represents minutes.
     */
    @Parcelize
    internal object Minutes : ClockDialMode() {
        @IgnoredOnParcel
        override val isHours = false
    }

    /**
     * @return `true` if this is [Hours].
     */
    internal abstract val isHours: Boolean
}