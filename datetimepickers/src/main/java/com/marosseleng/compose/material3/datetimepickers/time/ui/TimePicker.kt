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

package com.marosseleng.compose.material3.datetimepickers.time.ui

import android.text.format.DateFormat
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.marosseleng.compose.material3.datetimepickers.common.domain.getDefaultLocale
import com.marosseleng.compose.material3.datetimepickers.common.domain.roundToNearest
import com.marosseleng.compose.material3.datetimepickers.common.domain.withNotNull
import com.marosseleng.compose.material3.datetimepickers.time.domain.AmPmMode
import com.marosseleng.compose.material3.datetimepickers.time.domain.ClockDialMode
import com.marosseleng.compose.material3.datetimepickers.time.domain.LocalTimePickerColors
import com.marosseleng.compose.material3.datetimepickers.time.domain.LocalTimePickerShapes
import com.marosseleng.compose.material3.datetimepickers.time.domain.LocalTimePickerTypography
import com.marosseleng.compose.material3.datetimepickers.time.domain.TimePickerColors
import com.marosseleng.compose.material3.datetimepickers.time.domain.TimePickerDefaults
import com.marosseleng.compose.material3.datetimepickers.time.domain.TimePickerShapes
import com.marosseleng.compose.material3.datetimepickers.time.domain.TimePickerTypography
import com.marosseleng.compose.material3.datetimepickers.time.domain.getHour
import java.text.DateFormatSymbols
import java.time.LocalTime
import java.util.Calendar
import java.util.Locale
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.roundToInt
import kotlin.math.sqrt

/**
 * Displays a time picker.
 *
 * @param initialTime initial time to display.
 * @param onTimeChange function called each time the time is changed.
 * @param modifier [Modifier] passed from parent.
 * @param locale [Locale] used to translate AM/PM strings
 * @param is24HourFormat whether or not the time picker should be shown in 24-hour format.
 */
@ExperimentalMaterial3Api
@Composable
public fun TimePicker(
    initialTime: LocalTime,
    onTimeChange: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    locale: Locale = LocalConfiguration.current.getDefaultLocale(),
    is24HourFormat: Boolean = DateFormat.is24HourFormat(LocalContext.current),
    colors: TimePickerColors = TimePickerDefaults.colors,
    shapes: TimePickerShapes = TimePickerDefaults.shapes,
    typography: TimePickerTypography = TimePickerDefaults.typography,
) {
    var time: LocalTime by rememberSaveable {
        mutableStateOf(initialTime)
    }
    val hourFormat: ClockDialMode by rememberSaveable(is24HourFormat) {
        mutableStateOf(ClockDialMode.Hours(is24HourFormat = is24HourFormat))
    }
    var selectedMode: ClockDialMode by rememberSaveable {
        mutableStateOf(hourFormat)
    }
    val amPmMode: AmPmMode by rememberSaveable(time, is24HourFormat) {
        val result = if (is24HourFormat) {
            AmPmMode.NONE
        } else if (time.hour <= 11) {
            AmPmMode.AM
        } else {
            AmPmMode.PM
        }
        mutableStateOf(result)
    }
    CompositionLocalProvider(
        LocalTimePickerColors provides colors,
        LocalTimePickerShapes provides shapes,
        LocalTimePickerTypography provides typography,
    ) {
        Surface(modifier = modifier) {
            Column(
                modifier = Modifier
            ) {
                HorizontalClockDigits(
                    modifier = Modifier
                        .fillMaxWidth(),
                    time = time,
                    selectedMode = selectedMode,
                    amPmMode = amPmMode,
                    onHourClick = { selectedMode = hourFormat },
                    onMinuteClick = { selectedMode = ClockDialMode.Minutes },
                    onAmClick = {
                        if (amPmMode == AmPmMode.PM) {
                            val newTime = LocalTime.of(time.hour - 12, time.minute)
                            time = newTime
                            onTimeChange(newTime)
                        }
                    },
                    onPmClick = {
                        if (amPmMode == AmPmMode.AM) {
                            val newTime = LocalTime.of(time.hour + 12, time.minute)
                            time = newTime
                            onTimeChange(newTime)
                        }
                    },
                    locale = locale,
                )
                Crossfade(
                    modifier = Modifier
                        .padding(top = 36.dp)
                        .align(Alignment.CenterHorizontally),
                    targetState = selectedMode
                ) { dialMode ->
                    when (dialMode) {
                        is ClockDialMode.Minutes -> {
                            MinutesDial(
                                value = time.minute,
                                onValueChange = {
                                    val newTime = LocalTime.of(time.hour, it)
                                    time = newTime
                                    onTimeChange(newTime)
                                },
                            )
                        }

                        is ClockDialMode.Hours -> {
                            if (dialMode.is24HourFormat) {
                                Hour24Dial(
                                    value = time.getHour(in24HourFormat = true),
                                    onValueChange = {
                                        val newTime = LocalTime.of(it, time.minute)
                                        time = newTime
                                        onTimeChange(newTime)
                                    },
                                    onTouchStop = {
                                        selectedMode = ClockDialMode.Minutes
                                    },
                                )
                            } else {
                                Hour12Dial(
                                    value = time.getHour(in24HourFormat = false),
                                    onValueChange = {
                                        val hour = if (amPmMode == AmPmMode.AM) {
                                            if (it == 12) {
                                                0
                                            } else {
                                                it
                                            }
                                        } else {
                                            if (it == 12) {
                                                it
                                            } else {
                                                it + 12
                                            }
                                        }
                                        val newTime = LocalTime.of(hour, time.minute)
                                        time = newTime
                                        onTimeChange(newTime)
                                    },
                                    onTouchStop = {
                                        selectedMode = ClockDialMode.Minutes
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Displays the digital representation of [time] in vertical layout.
 */
@Composable
internal fun HorizontalClockDigits(
    time: LocalTime,
    selectedMode: ClockDialMode,
    amPmMode: AmPmMode,
    onHourClick: () -> Unit,
    onMinuteClick: () -> Unit,
    onAmClick: () -> Unit,
    onPmClick: () -> Unit,
    locale: Locale,
    modifier: Modifier = Modifier,
) {
    val selectedFieldColor = LocalTimePickerColors.current.clockDigitsSelectedBackgroundColor
    val selectedFieldFontColor = LocalTimePickerColors.current.clockDigitsSelectedTextColor
    val selectedFieldStroke = LocalTimePickerColors.current.clockDigitsSelectedBorderStroke

    val deselectedFieldColor = LocalTimePickerColors.current.clockDigitsUnselectedBackgroundColor
    val deselectedFieldFontColor = LocalTimePickerColors.current.clockDigitsUnselectedTextColor
    val deselectedFieldStroke = LocalTimePickerColors.current.clockDigitsUnselectedBorderStroke

    val clockDigitsShape = LocalTimePickerShapes.current.clockDigitsShape

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .height(intrinsicSize = IntrinsicSize.Max)
        ) {
            Box(
                modifier = Modifier
                    .semantics {
                        selected = selectedMode.isHours
                    }
                    .size(width = 96.dp, height = 80.dp)
                    .clip(shape = clockDigitsShape)
                    .background(if (selectedMode.isHours) selectedFieldColor else deselectedFieldColor)
                    .withNotNull(if (selectedMode.isHours) selectedFieldStroke else deselectedFieldStroke) { stroke ->
                        border(
                            border = BorderStroke(stroke.thickness, stroke.color),
                            shape = clockDigitsShape,
                        )
                    }
                    .clickable(onClick = onHourClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "%02d".format(time.getHour(amPmMode == AmPmMode.NONE)),
                    style = LocalTimePickerTypography.current.digitsHour,
                    color = if (selectedMode.isHours) selectedFieldFontColor else deselectedFieldFontColor,
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = ":",
                    style = LocalTimePickerTypography.current.digitsColon,
                    color = LocalTimePickerColors.current.clockDigitsColonTextColor,
                )
            }
            Box(
                modifier = Modifier
                    .semantics {
                        selected = !selectedMode.isHours
                    }
                    .size(width = 96.dp, height = 80.dp)
                    .clip(shape = clockDigitsShape)
                    .background(if (!selectedMode.isHours) selectedFieldColor else deselectedFieldColor)
                    .withNotNull(if (!selectedMode.isHours) selectedFieldStroke else deselectedFieldStroke) { stroke ->
                        border(
                            border = BorderStroke(stroke.thickness, stroke.color),
                            shape = clockDigitsShape,
                        )
                    }
                    .clickable(onClick = onMinuteClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "%02d".format(time.minute),
                    style = LocalTimePickerTypography.current.digitsMinute,
                    color = if (!selectedMode.isHours) selectedFieldFontColor else deselectedFieldFontColor
                )
            }
            if (amPmMode != AmPmMode.NONE) {
                VerticalAmPmSwitch(
                    amPmMode = amPmMode,
                    onAmClick = onAmClick,
                    onPmClick = onPmClick,
                    locale = locale,
                )
            }
        }
    }
}

/**
 * Vertical AM/PM switch for use in vertical layout.
 */
@Composable
internal fun VerticalAmPmSwitch(
    amPmMode: AmPmMode,
    onAmClick: () -> Unit,
    onPmClick: () -> Unit,
    locale: Locale,
) {
    val amPmStrings = DateFormatSymbols.getInstance(locale).amPmStrings
    val selectedFieldColor = LocalTimePickerColors.current.amPmSwitchFieldSelectedBackgroundColor
    val selectedFontColor = LocalTimePickerColors.current.amPmSwitchFieldSelectedTextColor
    val unselectedFieldColor = LocalTimePickerColors.current.amPmSwitchFieldUnselectedBackgroundColor
    val unselectedFontColor = LocalTimePickerColors.current.amPmSwitchFieldUnselectedTextColor
    val amPmSwitchShape = LocalTimePickerShapes.current.amPmSwitchShape

    Column(
        modifier = Modifier
            .padding(start = 12.dp)
            .fillMaxHeight()
            .width(52.dp)
            .clip(amPmSwitchShape)
            .withNotNull(LocalTimePickerColors.current.amPmSwitchBorderDividerStroke) { stroke ->
                border(
                    border = BorderStroke(stroke.thickness, stroke.color),
                    shape = amPmSwitchShape,
                )
            }
    ) {
        Box(
            modifier = Modifier
                .semantics {
                    selected = amPmMode == AmPmMode.AM
                }
                .weight(1f)
                .fillMaxWidth()
                .background(if (amPmMode == AmPmMode.AM) selectedFieldColor else unselectedFieldColor)
                .clickable(onClick = onAmClick),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = amPmStrings[Calendar.AM],
                style = LocalTimePickerTypography.current.am,
                color = if (amPmMode == AmPmMode.AM) selectedFontColor else unselectedFontColor
            )
        }
        LocalTimePickerColors.current.amPmSwitchBorderDividerStroke?.also { stroke ->
            Divider(thickness = stroke.thickness, color = stroke.color)
        }
        Box(
            modifier = Modifier
                .semantics {
                    selected = amPmMode == AmPmMode.PM
                }
                .weight(1f)
                .fillMaxWidth()
                .background(if (amPmMode == AmPmMode.PM) selectedFieldColor else unselectedFieldColor)
                .clickable(onClick = onPmClick),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = amPmStrings[Calendar.PM],
                style = LocalTimePickerTypography.current.pm,
                color = if (amPmMode == AmPmMode.PM) selectedFontColor else unselectedFontColor
            )
        }
    }
}

/**
 * Minutes time picker dial.
 *
 * @param value minute value (0-59).
 * @param onValueChange called when minutes change (0-59).
 */
@Composable
internal fun MinutesDial(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    TouchRegisteringDial(
        modifier = modifier,
        onAngleAndDistanceRatioChange = { angle, _ ->
            val minuteAngle = if (angle < 0) {
                (angle + 360).roundToNearest(6f).roundToInt()
            } else {
                angle.roundToNearest(6f).roundToInt()
            }
            val minute = if (minuteAngle == 0 || minuteAngle == 360) {
                0
            } else {
                minuteAngle / 6
            }
            onValueChange(minute)
        },
        onTouchStop = {},
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(LocalTimePickerColors.current.dialCenterColor)
        )
        val outerCircleOffset = (-104).dp
        val stickLength = outerCircleOffset.times(-1)
        val selectedAngle = value * 6
        // line
        Box(
            modifier = Modifier
                .size(width = 2.dp, height = stickLength)
                .align(Alignment.Center)
                .rotate(selectedAngle.toFloat())
                .offset(0.dp, stickLength / -2)
                .zIndex(-10f)
                .background(LocalTimePickerColors.current.dialHandColor)
        )
        val has5MinSelected = (selectedAngle % 5) == 0
        for (number in (0 until 60 step 5)) {
            val angle = 360f / 60f * number
            // numbers
            HourNumber(
                modifier = Modifier
                    .size(44.dp)
                    .align(Alignment.Center)
                    .rotate(angle)
                    .offset(0.dp, outerCircleOffset)
                    .rotate(-angle)
                    .clip(CircleShape),
                isSelected = number == value,
                value = number,
            )
        }
        if (!has5MinSelected) {
            HourNumber(
                modifier = Modifier
                    .size(44.dp)
                    .align(Alignment.Center)
                    .rotate(selectedAngle.toFloat())
                    .offset(0.dp, outerCircleOffset)
                    .rotate(-(selectedAngle.toFloat()))
                    .clip(CircleShape),
                isSelected = true,
                value = null,
            )
        }
    }
}

/**
 * Hours in 12-hour format time picker dial.
 *
 * @param value hour value 1-12
 * @param onValueChange called when selected hour changes (1-12)
 */
@Composable
internal fun Hour12Dial(
    value: Int,
    onValueChange: (Int) -> Unit,
    onTouchStop: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TouchRegisteringDial(
        modifier = modifier,
        onAngleAndDistanceRatioChange = { angle, _ ->
            val hourAngle = if (angle < 0) {
                (angle + 360).roundToNearest(30f).roundToInt()
            } else {
                angle.roundToNearest(30f).roundToInt()
            }
            val hour = if (hourAngle == 0 || hourAngle == 360) {
                12
            } else {
                hourAngle / 30
            }
            onValueChange(hour)
        },
        onTouchStop = onTouchStop,
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(LocalTimePickerColors.current.dialCenterColor)
        )
        val outerCircleOffset = (-104).dp
        val stickLength = outerCircleOffset.times(-1)
        val selectedAngle = value * 30
        // line
        Box(
            modifier = Modifier
                .size(width = 2.dp, height = stickLength)
                .align(Alignment.Center)
                .rotate(selectedAngle.toFloat())
                .offset(0.dp, stickLength / -2)
                .zIndex(-10f)
                .background(LocalTimePickerColors.current.dialHandColor)
        )
        for (number in (1..12)) {
            val angle = 360f / 12f * number
            HourNumber(
                modifier = Modifier
                    .size(44.dp)
                    .align(Alignment.Center)
                    .rotate(angle)
                    .offset(0.dp, outerCircleOffset)
                    .rotate(-angle)
                    .clip(CircleShape),
                isSelected = value == number,
                value = number,
            )
        }
    }
}

/**
 * Hours in 24-hour format time picker dial.
 *
 * @param value 24h hour value (0-23)
 * @param onValueChange called when value changes (0-23)
 */
@Composable
internal fun Hour24Dial(
    value: Int,
    onValueChange: (Int) -> Unit,
    onTouchStop: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TouchRegisteringDial(
        modifier = modifier,
        onAngleAndDistanceRatioChange = { angle, ratio ->
            val hourAngle = if (angle < 0) {
                (angle + 360).roundToNearest(30f).roundToInt()
            } else {
                angle.roundToNearest(30f).roundToInt()
            }
            val isOuterCircle = ratio > 0.75f
            val hourInter = if (hourAngle == 0 || hourAngle == 360) {
                0
            } else {
                hourAngle / 30
            }
            val hour = if (isOuterCircle) {
                // AM
                hourInter
            } else {
                // PM
                hourInter + 12
            }
            onValueChange(hour)
        },
        onTouchStop = onTouchStop,
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(LocalTimePickerColors.current.dialCenterColor)
        )

        val outerCircleOffset = (-104).dp
        val innerCircleOffset = (-80).dp
        val isSelectingInner = value > 11
        val stickLength = if (isSelectingInner) {
            innerCircleOffset.times(-1)
        } else {
            outerCircleOffset.times(-1)
        }
        val selectedAngle = (value % 12) * 30

        Box(
            modifier = Modifier
                .size(width = 2.dp, height = stickLength)
                .align(Alignment.Center)
                .rotate(selectedAngle.toFloat())
                .offset(0.dp, stickLength / -2)
                .zIndex(-10f)
                .background(color = LocalTimePickerColors.current.dialHandColor)
        )
        for (number in (0..11)) {
            val angle = 360f / 12f * number
            // numbers
            HourNumber(
                modifier = Modifier
                    .size(44.dp)
                    .align(Alignment.Center)
                    .rotate(angle)
                    .offset(0.dp, outerCircleOffset)
                    .rotate(-angle)
                    .clip(CircleShape),
                isSelected = !isSelectingInner && (number == value),
                value = number,
            )

            // numbers
            val number24Hour = number + 12
            HourNumber(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center)
                    .rotate(angle)
                    .offset(0.dp, innerCircleOffset)
                    .rotate(-angle)
                    .clip(CircleShape),
                isSelected = isSelectingInner && (number24Hour == value),
                value = number24Hour,
            )
        }
    }
}

/**
 * Base for all dials. Registering taps ang drags.
 *
 * @param onAngleAndDistanceRatioChange changes when the touch angle and the ratio of touch's distance from center to
 *                                      the radius in px changes. Angle of 0 degrees is at 12 o'clock.
 * @param onTouchStop called when the touch event halts.
 */
@Composable
internal fun TouchRegisteringDial(
    onAngleAndDistanceRatioChange: (angle: Float, ratio: Float) -> Unit,
    onTouchStop: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val diameterDp = 256.dp
    val diameterPx = with(LocalDensity.current) { diameterDp.toPx() }
    val radiusPx = diameterPx / 2f
    var centerX: Float by remember {
        mutableStateOf(0f)
    }
    var centerY: Float by remember {
        mutableStateOf(0f)
    }
    var touchX: Float by remember {
        mutableStateOf(0f)
    }
    var touchY: Float by remember {
        mutableStateOf(0f)
    }
    val x: Float by remember {
        derivedStateOf { touchX - centerX }
    }
    val y: Float by remember {
        derivedStateOf { centerY - touchY }
    }
    val angleFloat: Float by remember {
        derivedStateOf { atan2(x, y) * 180f / PI.toFloat() }
    }
    val distanceFromCenter: Float by remember {
        derivedStateOf { sqrt((x * x) + (y * y)) }
    }
    val distanceFromCenterRatio: Float by remember {
        derivedStateOf {
            distanceFromCenter / radiusPx
        }
    }
    Box(
        modifier = modifier
            .size(diameterDp)
            .clip(CircleShape)
            .onGloballyPositioned {
                val windowBounds = it.boundsInWindow()
                centerX = windowBounds.size.width / 2f
                centerY = windowBounds.size.height / 2f
            }
            .pointerInput(Unit) {
                detectDragGestures(onDragEnd = onTouchStop, onDragCancel = onTouchStop) { change, _ ->
                    touchX = change.position.x
                    touchY = change.position.y
                    onAngleAndDistanceRatioChange(angleFloat, distanceFromCenterRatio)
                }
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    touchX = it.x
                    touchY = it.y
                    onAngleAndDistanceRatioChange(angleFloat, distanceFromCenterRatio)
                    onTouchStop()
                }
            }
            .background(LocalTimePickerColors.current.dialBackgroundColor),
        content = content,
    )
}

/**
 * Represents the single hour/minute number.
 *
 * @param isSelected whether this value is selected.
 * @param value value to print. `null` if there is just a dot (for minutes that are not multipliers of 5).
 */
@Composable
internal fun BoxScope.HourNumber(
    isSelected: Boolean,
    value: Int?,
    modifier: Modifier = Modifier,
) {
    // TODO: try to draw the boundary
    val foregroundColor = if (isSelected) {
        LocalTimePickerColors.current.dialNumberSelectedTextColor
    } else {
        LocalTimePickerColors.current.dialNumberUnselectedTextColor
    }
    val backgroundColor = if (isSelected) {
        LocalTimePickerColors.current.dialNumberSelectedBackgroundColor
    } else {
        LocalTimePickerColors.current.dialNumberUnselectedBackgroundColor
    }
    Box(
        modifier = modifier
            .background(backgroundColor)
            .zIndex(if (isSelected) -1f else 0f),
        contentAlignment = Alignment.Center,
    ) {
        if (value != null) {
            Text(
                text = "$value",
                style = LocalTimePickerTypography.current.dialNumber,
                color = foregroundColor,
            )
        } else {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .background(foregroundColor)
            )
        }
    }
}