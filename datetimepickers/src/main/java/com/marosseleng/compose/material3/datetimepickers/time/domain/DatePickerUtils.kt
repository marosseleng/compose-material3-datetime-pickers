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

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import kotlin.math.ceil

// TODO: UNIT TESTS!!

/**
 * Current year will be at coordinates [1;1].
 *
 * @param yearsPerRow how many years to get per row
 * @param rowsLess how many rows to load before the current row's list
 * @param rowsMore how many rows to load after the current row's list
 * @param currentYear the current year.
 * @return rows of years to display
 */
internal fun getYears(
    yearsPerRow: Int,
    rowsLess: Int,
    rowsMore: Int,
    currentYear: Int = Year.now().value,
): List<List<Int>> {
    val result = mutableListOf<List<Int>>()

    val startOfTheRow = (currentYear - 1)

    for (row in (rowsLess..rowsMore)) {
        val rowBeginning = startOfTheRow + (row * yearsPerRow)
        val currentRow = mutableListOf<Int>()
        (0 until yearsPerRow).forEach {
            currentRow.add(rowBeginning + it)
        }
        result.add(currentRow)
    }

    return result
}

/**
 * Returns data (days) to be displayed in the grid.
 *
 * @param yearMonth [YearMonth] to display
 * @param firstDayOfWeek the first day of week (usually Locale-depended)
 * @param today
 * @return list of rows to display
 */
internal fun getRowsToDisplay(yearMonth: YearMonth, firstDayOfWeek: DayOfWeek, today: LocalDate): List<DayOfMonth> {
    val firstDayLocalDate = yearMonth.atDay(1)
    val firstDay = firstDayLocalDate.dayOfWeek

    val difference = if (firstDay == firstDayOfWeek) {
        0
    } else if (firstDay > firstDayOfWeek) {
        firstDay.value - firstDayOfWeek.value
    } else {
        7 - (firstDayOfWeek.value - firstDay.value)
    }

    val result = mutableListOf<DayOfMonth>()
    if (difference != 0) {
        // first day of the month is NOT equal to the week's first day - there will be [difference] days from the past month
        for (i in difference downTo 1) {
            result.add(
                DayOfMonth(
                    actualDay = firstDayLocalDate.minusDays(i.toLong()),
                    isPreviousMonth = true,
                    isCurrentMonth = false,
                    isNextMonth = false,
                    isToday = false,
                )
            )
        }
    }

    var localDateToAdd = firstDayLocalDate
    while (YearMonth.from(localDateToAdd) == yearMonth) {
        result.add(
            DayOfMonth(
                actualDay = localDateToAdd,
                isPreviousMonth = false,
                isCurrentMonth = true,
                isNextMonth = false,
                isToday = localDateToAdd == today,
            )
        )
        localDateToAdd = localDateToAdd.plusDays(1L)
    }

    val resultSizeUpToSeven = ceil(result.size / 7.0).toInt() * 7
    val remainder = resultSizeUpToSeven - result.size
    if (remainder == 0) {
        return result
    }

    for (i in 0 until remainder) {
        result.add(
            DayOfMonth(
                actualDay = localDateToAdd.plusDays(i.toLong()),
                isPreviousMonth = false,
                isCurrentMonth = false,
                isNextMonth = true,
                isToday = false,
            )
        )
    }

    return result
}