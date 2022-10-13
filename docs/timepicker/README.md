# Time picker

A highly customizable time picker for Jetpack Compose. Material3 theme-compatible.

![demo-video](resources/timepicker-demo.webm.mp4)

## Usage

A base composable `fun TimePicker()` displays the time picker. It is recommended to wrap the time picker in an `(Alert)Dialog` composable.

An example of such usage can be seen in `fun TimePickerDialog()`.

There are 2 mandatory parameters for `TimePickerDialog`:

- `onDismissRequest`: called when user wants to dismiss the dialog without selecting the time;
- `onTimeChange`: called when user taps the confirmation button, the parameter is the `LocalTime` representing the selected time.

Example of `TimePickerDialog` usage:

    ```kotlin
    var isDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val (selectedTime, setSelectedTime) = rememberSaveable {
        mutableStateOf(LocalTime.now().noSeconds())
    }
    if (isDialogShown) {
        TimePickerDialog(
            onDismissRequest = { isDialogShown = false },
            initialTime = selectedTime,
            onTimeChange = {
                setSelectedTime(it)
                isDialogShown = false
            },
            title = { Text(text = "Select time") }
        )
    }
    ```

## Customization
The time picker is a highly customizable component. It allows to customize colors, shapes and typography.
You can pass custom shapes of the digit-box as well as the AM/PM box:

_It is not recommended to pass an anonymous instance of `TimePickerShapes`. Instead, create an implementation separately and use that to prevent creating a new instance each time the component is recomposed._

    ```kotlin
    TimePickerDialog(
        // ...
        shapes = object : TimePickerShapes {
            override val clockDigitsShape: Shape
                get() = CutCornerShape(topStart = 16.dp, topEnd = 3.dp, bottomStart = 0.dp, bottomEnd = 24.dp)
            override val amPmSwitchShape: Shape
                get() = CircleShape
        }
    )
    ```
produces
![custom-shapes](resources/time-picker-day-custom-shapes.png)

For default values see [TimePickerDefaults](../../datetimepickers/src/main/java/com/marosseleng/compose/material3/datetimepickers/time/domain/TimePickerDefaults.kt).