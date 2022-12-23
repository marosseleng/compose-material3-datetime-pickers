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

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junitpioneer.jupiter.cartesian.CartesianTest
import org.junitpioneer.jupiter.params.IntRangeSource
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

public class DatePickerUtilsTest {

    @CartesianTest
    public fun `getYears returns the correct number of rows, each with correct years, with currentYear in the center of the row`(
        @CartesianTest.Values(ints = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 22, 55, 101])
        yearsPerRow: Int,
        @IntRangeSource(from = 0, to = 20)
        rowsLess: Int,
        @IntRangeSource(from = 0, to = 20)
        rowsMore: Int,
    ) {
        val currentYear = 2022
        val years = getYears(
            yearsPerRow = yearsPerRow,
            rowsLess = rowsLess,
            rowsMore = rowsMore,
            currentYear = currentYear,
        )

        assertEquals(rowsLess + 1 + rowsMore, years.size)

        assertTrue(years.all { it.size == yearsPerRow })

        assertEquals(yearsPerRow / 2, years.first { currentYear in it }.indexOf(currentYear))
    }

    @CartesianTest
    public fun `getDaysToDisplay returns proper data`(
        @CartesianTest.Enum firstDayOfWeek: DayOfWeek,
        @IntRangeSource(from = 2010, to = 2030) year: Int,
        @CartesianTest.Enum month: Month,
    ) {
        val weekSize = DayOfWeek.values().size
        val lastDayOfWeekOrdinal = (firstDayOfWeek.ordinal + weekSize - 1) % weekSize
        val lastDayOfWeek = DayOfWeek.values()[lastDayOfWeekOrdinal]

        val yearMonth = YearMonth.of(year, month)
        val yearMonthLength = yearMonth.lengthOfMonth()

        val startOfMonth = LocalDate.of(year, month, 1)
        val startOfMonthDayOfWeek = startOfMonth.dayOfWeek
        val diffStart = startOfMonthDayOfWeek.value - firstDayOfWeek.value
        val startPadding = if (diffStart < 0) diffStart + 7 else diffStart


        val endOfMonth = yearMonth.atEndOfMonth()
        val endOfMonthDayOfWeek = endOfMonth.dayOfWeek
        val diffEnd = lastDayOfWeek.value - endOfMonthDayOfWeek.value
        val endPadding = if (diffEnd < 0) diffEnd + weekSize else diffEnd

        val days = getDaysToDisplay(
            yearMonth = YearMonth.of(year, month),
            firstDayOfWeek = firstDayOfWeek,
        )

        assertEquals(startPadding + yearMonthLength + endPadding, days.size)

        val previousMonthDays = days.take(startPadding)
        val followingMonthDays = days.takeLast(endPadding)
        val currentMonthDays = days.drop(startPadding).dropLast(endPadding)

        assertTrue {
            previousMonthDays.all { it.isPreviousMonth && !it.isCurrentMonth && !it.isNextMonth }
        }
        assertTrue {
            followingMonthDays.all { it.isNextMonth && !it.isCurrentMonth && !it.isPreviousMonth }
        }
        assertTrue {
            currentMonthDays.all { it.isCurrentMonth && !it.isPreviousMonth && !it.isNextMonth }
        }

        val previousYearMonth = yearMonth.minusMonths(1)
        assertTrue {
            previousMonthDays.all {
                val day = it.actualDay
                YearMonth.of(day.year, day.month) == previousYearMonth
            }
        }
        val nextYearMonth = yearMonth.plusMonths(1)
        assertTrue {
            followingMonthDays.all {
                val day = it.actualDay
                YearMonth.of(day.year, day.month) == nextYearMonth
            }
        }
        assertTrue {
            currentMonthDays.all {
                val day = it.actualDay
                YearMonth.of(day.year, day.month) == yearMonth
            }
        }
    }
}