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
package com.marosseleng.compose.material3.datetimepickers.snapshots

import app.cash.paparazzi.Paparazzi
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
public class SnapshotTest(
    @TestParameter
    public val locale: TestLocale,
    @TestParameter
    public val darkMode: DarkMode,
    @TestParameter
    public val config: Config,
) {

    @get: Rule
    public val rule: Paparazzi = Paparazzi(
        deviceConfig = config.deviceConfig.copy(
            softButtons = false,
            nightMode = darkMode.mode,
            locale = "${locale.language}-r${locale.region}",
        ),
        maxPercentDifference = 0.5,
        showSystemUi = false,
    )

    @Test
    @Suppress("JUnitMalformedDeclaration")
    public fun testPreviews(
        @TestParameter(valuesProvider = ShowkasePreviewProvider::class)
        componentPreview: ComponentPreview,
    ) {
        rule.snapshot {
            SnapshotTestTheme {
                componentPreview.content()
            }
        }
    }
}