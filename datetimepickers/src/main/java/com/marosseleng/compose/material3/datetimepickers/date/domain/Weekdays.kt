/*
 *    Copyright 2023 Maroš Šeleng
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

package com.marosseleng.compose.material3.datetimepickers.date.domain

import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

private val zhWeekdays: Map<DayOfWeek, String> = mapOf(
    DayOfWeek.MONDAY to "\u4e00",
    DayOfWeek.TUESDAY to "\u4e8c",
    DayOfWeek.WEDNESDAY to "\u4e09",
    DayOfWeek.THURSDAY to "\u56db",
    DayOfWeek.FRIDAY to "\u4e94",
    DayOfWeek.SATURDAY to "\u516d",
    DayOfWeek.SUNDAY to "\u65e5",
)

private fun localeWeekdays(locale: Locale): Map<DayOfWeek, String> {
    return enumValues<DayOfWeek>()
        .associateWith { dayOfWeek -> dayOfWeek.getDisplayName(TextStyle.NARROW, locale) }
}

/**
 * Retrieves the names of weekdays.
 */
internal fun getWeekdays(locale: Locale): Map<DayOfWeek, String> {
    return when {
        locale.language == "zh" -> zhWeekdays
        else -> localeWeekdays(locale)
    }
}