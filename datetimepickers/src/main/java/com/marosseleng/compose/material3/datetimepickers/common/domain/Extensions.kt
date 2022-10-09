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