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

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import com.marosseleng.compose.material3.datetimepickers.date.domain.DatePickerDefaults
import com.marosseleng.compose.material3.datetimepickers.date.domain.LocalDatePickerColors
import com.marosseleng.compose.material3.datetimepickers.date.domain.LocalDatePickerShapes
import com.marosseleng.compose.material3.datetimepickers.date.domain.LocalDatePickerTypography
import com.marosseleng.compose.material3.datetimepickers.time.domain.LocalTimePickerColors
import com.marosseleng.compose.material3.datetimepickers.time.domain.LocalTimePickerShapes
import com.marosseleng.compose.material3.datetimepickers.time.domain.LocalTimePickerTypography
import com.marosseleng.compose.material3.datetimepickers.time.domain.TimePickerDefaults

@Composable
internal fun SnapshotTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme(),
    ) {
        CompositionLocalProvider(
            LocalTimePickerColors provides TimePickerDefaults.colors(),
            LocalTimePickerShapes provides TimePickerDefaults.shapes(),
            LocalTimePickerTypography provides TimePickerDefaults.typography(),
            LocalDatePickerColors provides DatePickerDefaults.colors(),
            LocalDatePickerShapes provides DatePickerDefaults.shapes(),
            LocalDatePickerTypography provides DatePickerDefaults.typography(),
            LocalInspectionMode provides true,
            content = content,
        )
    }
}