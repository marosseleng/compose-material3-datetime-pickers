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

import androidx.compose.runtime.Stable
import java.time.LocalDate

/**
 * Helper class to represent days of month displayed in datepicker grid.
 */
@Stable
internal data class DayOfMonth(
    /**
     * Backing field for the actual day represented by this structure.
     */
    val actualDay: LocalDate,
    /**
     * Whether this day is from the previous month.
     */
    val isPreviousMonth: Boolean,
    /**
     * Whether this day is from the currently displayed month.
     */
    val isCurrentMonth: Boolean,
    /**
     * Whether this day is from the next month.
     */
    val isNextMonth: Boolean,
)