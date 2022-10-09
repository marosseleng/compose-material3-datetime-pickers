package com.marosseleng.compose.material3.datetimepickers.time.ui.dialog

import android.R
import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.DialogProperties
import com.marosseleng.compose.material3.datetimepickers.time.ui.TimePicker
import java.time.LocalTime

/**
 * Dialog containing time picker.
 *
 * @param onDismissRequest called when user wants to dismiss the dialog without selecting the time.
 * @param onTimeChange called when user taps the confirmation button.
 * @param initialTime initial time to show in the picker.
 */
@ExperimentalMaterial3Api
@Composable
public fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeChange: (LocalTime) -> Unit,
    initialTime: LocalTime = LocalTime.now(),
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    shape: Shape = AlertDialogDefaults.shape,
    containerColor: Color = AlertDialogDefaults.containerColor,
    iconContentColor: Color = AlertDialogDefaults.iconContentColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    textContentColor: Color = AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties(),
) {
    Log.e("==>", "Dialog recomposing")
    val (time, setTime) = rememberSaveable(initialTime) {
        mutableStateOf(initialTime)
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = { onTimeChange(time) }) {
                Text(stringResource(id = R.string.ok))
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(id = R.string.cancel))
            }
        },
        icon = icon,
        title = title,
        text = {
            TimePicker(initialTime = initialTime, onTimeChange = setTime)
        },
        shape = shape,
        containerColor = containerColor,
        iconContentColor = iconContentColor,
        titleContentColor = titleContentColor,
        textContentColor = textContentColor,
        tonalElevation = tonalElevation,
        properties = properties,
    )
}