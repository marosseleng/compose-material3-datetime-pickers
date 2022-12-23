# Date picker

A highly customizable date picker for Jetpack Compose. Material3 theme-compatible.

![demo-video](resources/date-picker-demo.gif)

## Usage

Currently, only a modal date picker with single date selection is implemented. Modal date input and picker for range selection are planned. Docked date picker is currently not planned.

To display a date picker dialog, call `fun DatePickerDialog()` from your composable.

There are 2 mandatory parameters for `DatePickerDialog`:

- `onDismissRequest`: called when user wants to dismiss the dialog without selecting the date;
- `onDateChange`: called when user taps the confirmation button, the parameter is the `LocalDate` representing the selected date.

Example of `DatePickerDialog` usage:

```kotlin
var isDialogShown: Boolean by rememberSaveable {
    mutableStateOf(false)
}
var date: LocalDate? by remember {
    mutableStateOf(null)
}
if (isDialogShown) {
    DatePickerDialog(
        onDismissRequest = { isDialogShown = false },
        onDateChange = {
            date = it
            isDialogShown = false
        },
        // Optional but recommended parameter to provide the title for the dialog
        title = { Text(text = "Select date") }
    )
}
```

## Customization
Similarly to the time picker, the majority of date picker looks can be customized. Custom colors, shapes and typography can be passed and applied.

For example:

```kotlin

// ...
shapes = object : DatePickerShapes {
    // ... other shape definitions
    override val currentMonthDaySelected: Shape
        get() = CutCornerShape(percent = 40)
    override val currentMonthDayToday: Shape
        get() = RoundedCornerShape(4.dp)
}
```
produces
![custom-shapes](resources/date-picker-night-custom-shapes.png)

For default values see [DatePickerDefaults](../../datetimepickers/src/main/java/com/marosseleng/compose/material3/datetimepickers/date/domain/DatePickerDefaults.kt).
