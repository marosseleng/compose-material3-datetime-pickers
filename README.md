# Compose material3 Date[^1] and Time pickers

![Build status](https://github.com/marosseleng/compose-material3-datetime-pickers/actions/workflows/gradle.yml/badge.svg)
![Maven Central](https://img.shields.io/maven-central/v/com.marosseleng.android/compose-material3-datetime-pickers)
![License](https://img.shields.io/github/license/marosseleng/compose-material3-datetime-pickers)

A Jetpack Compose components with material3 support for date* & time picking.

*Datepicker is currently a work in progress, but the time picker is ready for use!*

![timepicker](docs/timepicker/resources/time-picker-night.png)

## Latest version

### 0.2.0 ([Changelog](https://github.com/marosseleng/compose-material3-datetime-pickers/compare/v0.1.2...v0.2.0))

#### Kotlin version: `1.7.20`

#### Compose compiler version: `1.3.2`

#### Compose BOM version: `2022.12.00`
For details of packages inside a BOM, download it's POM file from [Google's maven repository](https://maven.google.com/web/index.html?q=compose-bom#androidx.compose:compose-bom).

#### Material3 version: `1.0.1`

## Usage
To use this library in your project, in your app module's `build.gradle.kts` add:

```kotlin
dependencies {
    implementation("com.marosseleng.android:compose-material3-datetime-pickers:<LATEST_VERSION>")
}
```

#### For timepicker instructions see its [readme](docs/timepicker/README.md)

[^1]: Date picker is still a work in progress.