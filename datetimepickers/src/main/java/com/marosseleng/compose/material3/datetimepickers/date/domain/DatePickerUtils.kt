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

import com.marosseleng.compose.material3.datetimepickers.time.domain.DayOfMonth
import java.time.DayOfWeek
import java.time.Year
import java.time.YearMonth
import kotlin.math.ceil

/**
 * Returns the chunked list of years at [yearsPerRow] years per row.
 *
 * Tries to position the [currentYear] at the center of the second row.
 * If there are not enough rows to position the [currentYear] on the second row, it is positioned on the first row.
 * Horizontal position of [currentYear] is [yearsPerRow]/2.
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

    val rightPadding = (yearsPerRow - 1) / 2
    val leftPadding = if (yearsPerRow % 2 == 0) {
        ((yearsPerRow - 1) / 2) + 1
    } else {
        rightPadding
    }

    val currentYearRowStart = currentYear - leftPadding
    val currentYearRowEnd = currentYear + rightPadding

    // previous years
    if (rowsLess > 0) {
        val previousYearsStart = currentYearRowStart - (rowsLess * yearsPerRow)
        val previousYearsEndInclusive = currentYearRowStart - 1
        result.addAll((previousYearsStart..previousYearsEndInclusive).chunked(yearsPerRow))
    }
    // current year row
    result.add((currentYearRowStart..currentYearRowEnd).toList())
    // following years
    if (rowsMore > 0) {
        val followingYearsStart = currentYearRowEnd + 1
        val followingYearsEndInclusive = currentYearRowEnd + (rowsMore * yearsPerRow)
        result.addAll((followingYearsStart..followingYearsEndInclusive).chunked(yearsPerRow))
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
internal fun getDaysToDisplay(yearMonth: YearMonth, firstDayOfWeek: DayOfWeek): List<DayOfMonth> {
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
            )
        )
    }

    return result
}