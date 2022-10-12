package com.marosseleng.compose.material3.datetimepickers.time.ui

import android.text.format.DateFormat
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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

@ExperimentalMaterial3Api
@Composable
internal fun HorizontalTimePicker(
    initialTime: LocalTime,
    onTimeChange: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
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
            Row(
                modifier = Modifier
            ) {
                VerticalClockDigits(
                    modifier = Modifier.align(Alignment.CenterVertically),
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
                )
                Crossfade(
                    modifier = Modifier.padding(start = 64.dp, top = 8.dp, bottom = 24.dp),
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
internal fun VerticalClockDigits(
    time: LocalTime,
    selectedMode: ClockDialMode,
    amPmMode: AmPmMode,
    onHourClick: () -> Unit,
    onMinuteClick: () -> Unit,
    onAmClick: () -> Unit,
    onPmClick: () -> Unit,
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
        Column(
            modifier = Modifier.width(216.dp)
        ) {
            Row(modifier = Modifier.height(80.dp)) {
                Box(
                    modifier = Modifier
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
            }
            if (amPmMode != AmPmMode.NONE) {
                HorizontalAmPmSwitch(
                    amPmMode = amPmMode,
                    onAmClick = onAmClick,
                    onPmClick = onPmClick,
                    modifier = Modifier.padding(top = 12.dp),
                )
            }
        }
    }
}

@Composable
public fun VerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
) {
    val targetThickness = if (thickness == Dp.Hairline) {
        (1f / LocalDensity.current.density).dp
    } else {
        thickness
    }
    Box(
        modifier
            .fillMaxHeight()
            .width(targetThickness)
            .background(color = color)
    )
}


/**
 * Vertical AM/PM switch for use in vertical layout.
 */
@Composable
internal fun HorizontalAmPmSwitch(
    amPmMode: AmPmMode,
    onAmClick: () -> Unit,
    onPmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val amPmStrings = DateFormatSymbols.getInstance(Locale.getDefault()).amPmStrings
    val selectedFieldColor = LocalTimePickerColors.current.amPmSwitchFieldSelectedBackgroundColor
    val selectedFontColor = LocalTimePickerColors.current.amPmSwitchFieldSelectedTextColor
    val unselectedFieldColor = LocalTimePickerColors.current.amPmSwitchFieldUnselectedBackgroundColor
    val unselectedFontColor = LocalTimePickerColors.current.amPmSwitchFieldUnselectedTextColor
    val amPmSwitchShape = LocalTimePickerShapes.current.amPmSwitchShape

    Row(
        modifier = modifier
            .size(width = 216.dp, height = 40.dp)
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
                .weight(1f)
                .fillMaxHeight()
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
            VerticalDivider(thickness = stroke.thickness, color = stroke.color)
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
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

// 572
// 388?
@ExperimentalMaterial3Api
@Composable
@Preview(widthDp = 536)
internal fun HorizontalPickerPreview() {
    val (time, setTime) = remember {
        mutableStateOf(LocalTime.now())
    }
    MaterialTheme {
        HorizontalTimePicker(modifier = Modifier.fillMaxWidth(), initialTime = time, onTimeChange = setTime)
    }
}