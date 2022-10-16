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

import java.time.LocalTime

/**
 * Constructs instance of [LocalTime] with hours and minutes only.
 */
public fun LocalTime.noSeconds(): LocalTime {
    return LocalTime.of(hour, minute)
}

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