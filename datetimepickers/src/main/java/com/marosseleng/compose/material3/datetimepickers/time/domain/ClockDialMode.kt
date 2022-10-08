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