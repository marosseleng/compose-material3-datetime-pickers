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

package com.marosseleng.compose.material3.datetimepickers

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.Month

@OptIn(ExperimentalComposeUiApi::class)
public class DatePickerDialogTest {

    @get:Rule
    public val rule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity> = createAndroidComposeRule()

    @Test
    public fun datePickerDialogConfirmDate() {
        val today = LocalDate.of(2023, Month.JANUARY, 1)
        val daysToAdd = 13
        val monthsToAdd = 18
        val monthsToSubtract = 4
        val expectedDate = today
            .plusDays(daysToAdd.toLong())
            .plusMonths(monthsToAdd.toLong())
            .minusMonths(monthsToSubtract.toLong())
        var selectedDate: LocalDate? = null

        rule.setContent {
            MaterialTheme {
                DatePickerDialog(
                    onDismissRequest = { selectedDate = null },
                    onDateChange = { selectedDate = it },
                    initialDate = null,
                    today = today,
                    showDaysAbbreviations = false,
                    highlightToday = false,
                )
            }
        }

        val nextMonthBox = hasClickAction() and hasContentDescription(rule.activity.getString(R.string.datepicker_next_month))
        repeat(monthsToAdd) {
            rule.onNode(nextMonthBox).performClick()
        }

        val previousMonthBox = hasClickAction() and hasContentDescription(rule.activity.getString(R.string.datepicker_previous_month))
        repeat(monthsToSubtract) {
            rule.onNode(previousMonthBox).performClick()
        }

        val dayBox = hasClickAction() and hasText(expectedDate.dayOfMonth.toString())

        rule.onNode(dayBox).performClick()

        val positiveButton = hasClickAction() and hasText(rule.activity.getString(android.R.string.ok))

        rule.onNode(positiveButton).performClick()

        assert(expectedDate == selectedDate)
    }

    @Test
    public fun datePickerDialogCancelDate() {
        val today = LocalDate.of(2023, Month.JANUARY, 1)
        val daysToAdd = 13
        val monthsToAdd = 18
        val monthsToSubtract = 4
        val expectedDate = today
            .plusDays(daysToAdd.toLong())
            .plusMonths(monthsToAdd.toLong())
            .minusMonths(monthsToSubtract.toLong())
        var selectedDate: LocalDate? = null

        rule.setContent {
            MaterialTheme {
                DatePickerDialog(
                    onDismissRequest = { selectedDate = null },
                    onDateChange = { selectedDate = it },
                    initialDate = null,
                    today = today,
                    showDaysAbbreviations = false,
                    highlightToday = false,
                )
            }
        }

        val nextMonthBox = hasClickAction() and hasContentDescription(rule.activity.getString(R.string.datepicker_next_month))
        repeat(monthsToAdd) {
            rule.onNode(nextMonthBox).performClick()
        }

        val previousMonthBox = hasClickAction() and hasContentDescription(rule.activity.getString(R.string.datepicker_previous_month))
        repeat(monthsToSubtract) {
            rule.onNode(previousMonthBox).performClick()
        }

        val dayBox = hasClickAction() and hasText(expectedDate.dayOfMonth.toString())

        rule.onNode(dayBox).performClick()

        val negativeButton = hasClickAction() and hasText(rule.activity.getString(android.R.string.cancel))

        rule.onNode(negativeButton).performClick()

        assert(selectedDate == null)
    }
}