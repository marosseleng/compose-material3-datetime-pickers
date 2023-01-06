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
package com.marosseleng.compose.material3.datetimepickers.date.ui

import androidx.compose.ui.ExperimentalComposeUiApi
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.resources.NightMode
import com.android.resources.ScreenOrientation
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class)
@RunWith(TestParameterInjector::class)
public class DatePickerSnapshotTest(
    @TestParameter
    config: Config,
    @TestParameter
    orientation: TestOrientation,
    @TestParameter
    darkModeSetting: DarkModeSetting,
    @TestParameter
    public val locale: TestLocale,
) {

    public enum class Config(
        public val deviceConfig: DeviceConfig,
    ) {
        PIXEL_4A(deviceConfig = DeviceConfig.PIXEL_4A),
    }

    public enum class TestLocale(
        public val language: String,
        public val region: String
    ) {
        EN_US("en", "US"),
        SK_SK("sk", "SK"),
    }

    public enum class TestOrientation(
        public val orientation: ScreenOrientation,
    ) {
        PORTRAIT(ScreenOrientation.PORTRAIT),
        LANDSCAPE(ScreenOrientation.LANDSCAPE),
    }

    public enum class DarkModeSetting(
        public val nightMode: NightMode,
    ) {
        NIGHT(NightMode.NIGHT),
        NOTNIGHT(NightMode.NOTNIGHT),
    }

    @get: Rule
    public val rule: Paparazzi = Paparazzi(
        deviceConfig = config.deviceConfig.copy(
            softButtons = false,
            orientation = orientation.orientation,
            nightMode = darkModeSetting.nightMode,
            locale = "${locale.language}-r${locale.region}"
        ),
        showSystemUi = false
    )

    @Test
    public fun testDatePicker() {
        rule.snapshot {
            DatePickerDialog(
                initialDate = LocalDate.of(2023, java.time.Month.JANUARY, 1),
                onDismissRequest = {},
                onDateChange = {},
                locale = Locale(locale.language, locale.region),
            )
        }
    }
}