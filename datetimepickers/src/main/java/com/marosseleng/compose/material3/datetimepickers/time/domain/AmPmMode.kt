package com.marosseleng.compose.material3.datetimepickers.time.domain

/**
 * Describes possible states of AM/PM in time picker.
 */
internal enum class AmPmMode {
    /**
     * Represents picking AM time in 12-hour format.
     */
    AM,
    
    /**
     * Represents picking PM time in 12-hour format.
     */
    PM,

    /**
     * Represents none of the above. Typically because the clock is in 24-hour format.
     */
    NONE,
}