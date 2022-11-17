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

package com.marosseleng.compose.material3.datetimepickers.common.domain

import androidx.compose.ui.Modifier
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Rounds [this] to the nearest [increment].
 *
 * @return [this] rounded to the nearest increment.
 */
internal fun Float.roundToNearest(increment: Float): Float {
    if (abs(increment) <= 0.00001) {
        // 0 increment does not make much sense, but prevent division by 0
        return 0f
    }
    return (this / increment).roundToInt() * increment
}

/**
 * Calls the given [callback] only if [obj] is not `null`.
 */
public fun <T : Any?> Modifier.withNotNull(obj: T, callback: Modifier.(T & Any) -> Modifier): Modifier {
    return if (obj == null) {
        this
    } else {
        callback(obj)
    }
}

public fun Modifier.applyIf(condition: Boolean, callback: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        this.callback()
    } else {
        this
    }
}