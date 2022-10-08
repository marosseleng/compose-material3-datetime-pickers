package com.marosseleng.compose.material3.datetimepickers.time.domain

import java.time.LocalTime

/**
 * Returns the hour portion of this [LocalTime].
 *
 * @param in24HourFormat if true returns value in range [0..23], otherwise returns value in range [1..12].
 */
internal fun LocalTime.getHour(in24HourFormat: Boolean) = if (in24HourFormat) {
    hour
} else {
    val modulo = hour % 12
    if (modulo == 0) {
        12
    } else {
        modulo
    }
}