package com.marosseleng.compose.material3.datetimepickers

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.marosseleng.compose.material3.datetimepickers.common.domain.roundToNearest
import com.marosseleng.compose.material3.datetimepickers.time.domain.AmPmMode
import com.marosseleng.compose.material3.datetimepickers.time.domain.ClockDialMode
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
 * @param modifier [Modifier] passed from parent.
 * @param is24HourFormat whether or not the time picker should be shown in 24-hour format.
 * @param time time to display.
 * @param onTimeChange function called each time the time is changed.
 */
@ExperimentalMaterial3Api
@Composable
public fun TimePicker(
    modifier: Modifier = Modifier,
    is24HourFormat: Boolean = DateFormat.is24HourFormat(LocalContext.current),
    time: LocalTime,
    onTimeChange: (LocalTime) -> Unit,
) {
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
    Surface(modifier = modifier) {
        Column(
            modifier = Modifier
        ) {
            // TODO:
            Text(text = "Select time", style = MaterialTheme.typography.labelLarge)
            HorizontalClockDigits(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                time = time,
                selectedMode = selectedMode,
                amPmMode = amPmMode,
                onHourClick = { selectedMode = hourFormat },
                onMinuteClick = { selectedMode = ClockDialMode.Minutes },
                onAmClick = {
                    if (amPmMode == AmPmMode.PM) {
                        onTimeChange(LocalTime.of(time.hour - 12, time.minute))
                    }
                },
                onPmClick = {
                    if (amPmMode == AmPmMode.AM) {
                        onTimeChange(LocalTime.of(time.hour + 12, time.minute))
                    }
                },
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
                                onTimeChange(LocalTime.of(time.hour, it))
                            },
                        )
                    }
                    is ClockDialMode.Hours -> {
                        if (dialMode.is24HourFormat) {
                            Hour24Dial(
                                value = time.getHour(in24HourFormat = true),
                                onValueChange = {
                                    onTimeChange(LocalTime.of(it, time.minute))
                                },
                                onDragStop = {
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
                                    onTimeChange(LocalTime.of(hour, time.minute))
                                },
                                onDragStop = {
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

/**
 * Displays the digital representation of [time] in vertical layout.
 */
@Composable
internal fun HorizontalClockDigits(
    modifier: Modifier = Modifier,
    time: LocalTime,
    selectedMode: ClockDialMode,
    amPmMode: AmPmMode,
    onHourClick: () -> Unit,
    onMinuteClick: () -> Unit,
    onAmClick: () -> Unit,
    onPmClick: () -> Unit,
) {
    val selectedFieldColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.24f)
    val selectedFieldFontColor = MaterialTheme.colorScheme.primary
    val deselectedFieldColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.24f)
    val deselectedFieldFontColor = MaterialTheme.colorScheme.onSurface

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
                    .size(width = 96.dp, height = 80.dp)
                    .clip(shape = MaterialTheme.shapes.small)
                    .background(if (selectedMode.isHours) selectedFieldColor else deselectedFieldColor)
                    .clickable(onClick = onHourClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "%02d".format(time.getHour(amPmMode == AmPmMode.NONE)),
                    style = MaterialTheme.typography.displayMedium.copy(textAlign = TextAlign.Center),
                    color = if (selectedMode.isHours) selectedFieldFontColor else deselectedFieldFontColor
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
                    style = MaterialTheme.typography.displayMedium.copy(textAlign = TextAlign.Center),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Box(
                modifier = Modifier
                    .size(width = 96.dp, height = 80.dp)
                    .clip(shape = MaterialTheme.shapes.small)
                    .background(if (!selectedMode.isHours) selectedFieldColor else deselectedFieldColor)
                    .clickable(onClick = onMinuteClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "%02d".format(time.minute),
                    style = MaterialTheme.typography.displayMedium.copy(textAlign = TextAlign.Center),
                    color = if (!selectedMode.isHours) selectedFieldFontColor else deselectedFieldFontColor
                )
            }
            if (amPmMode != AmPmMode.NONE) {
                VerticalAmPmSwitch(amPmMode = amPmMode, onAmClick = onAmClick, onPmClick = onPmClick)
            }
        }
    }
}

/**
 * FINAL
 *
 * Vertical AM/PM switch for use in vertical layout.
 */
@Composable
internal fun VerticalAmPmSwitch(
    amPmMode: AmPmMode,
    onAmClick: () -> Unit,
    onPmClick: () -> Unit,
) {
    val amPmStrings = DateFormatSymbols.getInstance(Locale.getDefault()).amPmStrings
    val selectedFieldColor = MaterialTheme.colorScheme.tertiary.copy(0.24f)
    val selectedFontColor = MaterialTheme.colorScheme.tertiary
    val unselectedFieldColor = Color.Transparent
    val unselectedFontColor = MaterialTheme.colorScheme.onSurface
    Column(
        modifier = Modifier
            .padding(start = 12.dp)
            .fillMaxHeight()
            .width(52.dp)
            .clip(MaterialTheme.shapes.small)
            .border(
                border = BorderStroke(Dp.Hairline, MaterialTheme.colorScheme.outline),
                shape = MaterialTheme.shapes.small
            )
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(if (amPmMode == AmPmMode.AM) selectedFieldColor else unselectedFieldColor)
                .clickable(onClick = onAmClick),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = amPmStrings[Calendar.AM],
                style = MaterialTheme.typography.titleMedium,
                color = if (amPmMode == AmPmMode.AM) selectedFontColor else unselectedFontColor
            )
        }
        Divider(thickness = Dp.Hairline, color = MaterialTheme.colorScheme.outline)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(if (amPmMode == AmPmMode.PM) selectedFieldColor else unselectedFieldColor)
                .clickable(onClick = onPmClick),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = amPmStrings[Calendar.PM],
                style = MaterialTheme.typography.titleMedium,
                color = if (amPmMode == AmPmMode.PM) selectedFontColor else unselectedFontColor
            )
        }
    }
}

/**
 * @param value minute value (0-59)
 * @param onValueChange called when minutes change (0-59)
 */
@Composable
fun MinutesDial(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChange: (Int) -> Unit,
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
        onDragStop = {},
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
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
                .background(color = MaterialTheme.colorScheme.primary)
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
 * @param value hour value 1-12
 * @param onValueChange called when selected hour changes (1-12)
 */
@Composable
fun Hour12Dial(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChange: (Int) -> Unit,
    onDragStop: () -> Unit,
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
        onDragStop = onDragStop,
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
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
                .background(color = MaterialTheme.colorScheme.primary)
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
 * @param value 24h hour value (0-23)
 * @param onValueChange called when value changes (0-23)
 */
@Composable
fun Hour24Dial(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChange: (Int) -> Unit,
    onDragStop: () -> Unit,
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
        onDragStop = onDragStop,
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
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
                .background(color = MaterialTheme.colorScheme.primary)
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

@Composable
fun TouchRegisteringDial(
    modifier: Modifier = Modifier,
    onAngleAndDistanceRatioChange: (angle: Float, ratio: Float) -> Unit,
    onDragStop: () -> Unit,
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
                detectDragGestures(onDragEnd = onDragStop, onDragCancel = onDragStop) { change, _ ->
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
                }
            }
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.24f)),
        content = content,
    )
}

@Composable
fun BoxScope.HourNumber(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    value: Int?,
) {
    // TODO
    val foregroundColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    Box(
        modifier = modifier
            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
            .zIndex(if (isSelected) -1f else 0f),
        contentAlignment = Alignment.Center,
    ) {
        if (value != null) {
            Text(
                text = "$value",
                style = MaterialTheme.typography.titleSmall,
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