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

package com.marosseleng.compose.material3.datetimepickers.common.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * Handler allowing composables using [LazyListState] to scroll possibly infinitely in both directions.
 *
 * @param listState state of the list that needs to also be passed to the LazyColumn composable.
 * @param threshold the number of items before reaching the start/end of the list
 * @param onLoadPrevious called when the start of the list reaches the given [threshold]
 * @param onLoadNext called when the end of the list reaches the given [threshold]
 */
@Composable
internal fun BidirectionalInfiniteListHandler(
    listState: LazyListState,
    threshold: Int = 2,
    onLoadPrevious: () -> Unit,
    onLoadNext: () -> Unit
) {
    val loadPrevious = remember {
        derivedStateOf {
            listState.firstVisibleItemIndex < threshold
        }
    }

    val loadNext = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItemsNumber - threshold)
        }
    }

    LaunchedEffect(loadPrevious, loadNext) {
        combine(
            flow = snapshotFlow { loadPrevious.value },
            flow2 = snapshotFlow { loadNext.value },
            transform = { previous, next -> previous to next }
        ).distinctUntilChanged()
            .collect { (loadPrevious, loadNext) ->
                if (loadPrevious) {
                    onLoadPrevious()
                }
                if (loadNext) {
                    onLoadNext()
                }
            }
    }
}