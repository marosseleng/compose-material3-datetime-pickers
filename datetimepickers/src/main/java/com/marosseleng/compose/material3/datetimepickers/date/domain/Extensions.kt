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

package com.marosseleng.compose.material3.datetimepickers.date.domain

import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

/**
 * Formats the given [YearMonth] for usage in DatePicker.
 *
 * This method is definitely not ideal, because it does not year/month ordering of each Locale. But is enough for now.
 */
public fun YearMonth.getDisplayName(locale: Locale): String {
    return "${month.getDisplayName(TextStyle.FULL_STANDALONE, locale)}${Typography.nbsp}$year"
}