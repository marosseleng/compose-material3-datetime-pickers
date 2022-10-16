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