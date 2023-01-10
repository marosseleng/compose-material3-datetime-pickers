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

package com.marosseleng.compose.material3.datetimepickers

import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.marosseleng.compose.material3.datetimepickers.common.domain.getDefaultLocale
import com.marosseleng.compose.material3.datetimepickers.time.ui.dialog.TimePickerDialog
import org.junit.Rule
import org.junit.Test
import java.text.DateFormatSymbols
import java.time.LocalTime
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
public class TimePickerDialogTest {

    @get:Rule
    public val rule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity> =
        createAndroidComposeRule()

    private val hourDial = (1..12)
        .map { hasAnyChild(hasText("$it")) }
        .reduce { acc, matcher -> acc and matcher }

    private val minuteDial = (0..55 step 5)
        .map { hasAnyChild(hasText("$it")) }
        .reduce { acc, matcher -> acc and matcher }

    private fun getDigitMatcher(hour: Int, minute: Int, isHour: Boolean) =
        hasText("%02d".format(if (isHour) hour else minute)) and hasClickAction() and hasAnySibling(
            hasText(
                "%02d".format(
                    if (isHour) minute else hour
                )
            )
        )

    @Test
    public fun timePickerDialogConfirmTime(): Unit = with(rule) {
        val initialHours = 7
        val initialMinutes = 17
        val initialTime = LocalTime.of(initialHours, initialMinutes, 0, 0)
        val expectedHours12HourMode = 3
        val expectedHours24HourMode = 15
        val expectedMinutes = 55

        var selectedTime: LocalTime? = null

        val locale = activity.resources.configuration.getDefaultLocale()
        val amPmStrings = DateFormatSymbols.getInstance(locale).amPmStrings

        setContent {
            TimePickerDialog(
                onDismissRequest = { selectedTime = null },
                onTimeChange = { selectedTime = it },
                initialTime = initialTime,
                locale = locale,
                is24HourFormat = false,
            )
        }

        onNode(hourDial).assertExists()
        onNode(getDigitMatcher(initialHours, initialMinutes, isHour = true)).assertIsSelected()
        onNode(getDigitMatcher(initialHours, initialMinutes, isHour = false)).assertIsNotSelected()
        onNode(hasText(amPmStrings[Calendar.AM])).assertIsSelected()
        onNode(hasText(amPmStrings[Calendar.PM])).assertIsNotSelected()

        onNode(hasText("$expectedHours12HourMode") and hasParent(hourDial)).performClick()

        waitForIdle()
        onNode(hourDial).assertDoesNotExist()
        onNode(minuteDial).assertExists()

        onNode(getDigitMatcher(expectedHours12HourMode, initialMinutes, isHour = true)).assertIsNotSelected()
        onNode(getDigitMatcher(expectedHours12HourMode, initialMinutes, isHour = false)).assertIsSelected()

        onNode(hasText(amPmStrings[Calendar.PM])).performClick()

        onNode(hasText(amPmStrings[Calendar.AM])).assertIsNotSelected()
        onNode(hasText(amPmStrings[Calendar.PM])).assertIsSelected()

        onNode(hasText("$expectedMinutes") and hasParent(minuteDial)).performClick()
        onNode(getDigitMatcher(expectedHours12HourMode, expectedMinutes, isHour = false)).assertIsSelected()
        onNode(getDigitMatcher(expectedHours12HourMode, expectedMinutes, isHour = true)).assertIsNotSelected()

        onNode(hasText(activity.getString(android.R.string.ok))).performClick()

        assert(LocalTime.of(expectedHours24HourMode, expectedMinutes, 0, 0) == selectedTime)
    }

    @Test
    public fun timePickerDialogCancelTime(): Unit = with(rule) {
        val initialHours = 7
        val initialMinutes = 17
        val initialTime = LocalTime.of(initialHours, initialMinutes, 0, 0)
        val expectedHours12HourMode = 3
        val expectedMinutes = 55

        var selectedTime: LocalTime? = LocalTime.of(1, 1, 9, 9)

        val locale = activity.resources.configuration.getDefaultLocale()
        val amPmStrings = DateFormatSymbols.getInstance(locale).amPmStrings

        setContent {
            TimePickerDialog(
                onDismissRequest = { selectedTime = null },
                onTimeChange = { selectedTime = it },
                initialTime = initialTime,
                locale = locale,
                is24HourFormat = false,
            )
        }

        onNode(hourDial).assertExists()
        onNode(getDigitMatcher(initialHours, initialMinutes, isHour = true)).assertIsSelected()
        onNode(getDigitMatcher(initialHours, initialMinutes, isHour = false)).assertIsNotSelected()
        onNode(hasText(amPmStrings[Calendar.AM])).assertIsSelected()
        onNode(hasText(amPmStrings[Calendar.PM])).assertIsNotSelected()

        onNode(hasText("$expectedHours12HourMode") and hasParent(hourDial)).performClick()

        waitForIdle()
        onNode(hourDial).assertDoesNotExist()
        onNode(minuteDial).assertExists()

        onNode(getDigitMatcher(expectedHours12HourMode, initialMinutes, isHour = true)).assertIsNotSelected()
        onNode(getDigitMatcher(expectedHours12HourMode, initialMinutes, isHour = false)).assertIsSelected()

        onNode(hasText(amPmStrings[Calendar.PM])).performClick()

        onNode(hasText(amPmStrings[Calendar.AM])).assertIsNotSelected()
        onNode(hasText(amPmStrings[Calendar.PM])).assertIsSelected()

        onNode(hasText("$expectedMinutes") and hasParent(minuteDial)).performClick()
        onNode(getDigitMatcher(expectedHours12HourMode, expectedMinutes, isHour = false)).assertIsSelected()
        onNode(getDigitMatcher(expectedHours12HourMode, expectedMinutes, isHour = true)).assertIsNotSelected()

        onNode(hasText(activity.getString(android.R.string.cancel))).performClick()

        assert(selectedTime == null)
    }
}