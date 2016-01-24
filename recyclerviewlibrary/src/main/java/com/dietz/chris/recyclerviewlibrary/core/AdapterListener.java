/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dietz.chris.recyclerviewlibrary.core;

import android.support.annotation.NonNull;

/**
 * Internal listener for the adaptier items to communicate within themselves.  Don't make this public.
 * DON'T DO IT.
 */
interface AdapterListener {
    /**
     * The underlying data of an adapter item has changed.
     */
    void itemChanged(@NonNull AdapterItem item);

    /**
     * An item was added to the provided container.
     * @param container
     *      Container that now has a new added item.
     * @param item
     *      Item that was added.
     * @param atPosition
     *      Position in which the item was added from within the container.
     */
    void itemAdded(@NonNull AdapterItemGroup container, @NonNull AdapterItem item, int atPosition);

    /**
     * Multple items have been removed from the provided container.
     * @param container
     *      Container that had the items added.
     * @param fromPosition
     *      Starting position from the first item that was added.
     * @param size
     *      Number of items that were added.
     */
    void itemsAdded(@NonNull AdapterItemGroup container, int fromPosition, int size);

    /**
     * An item was removed from the provided container.
     * @param container
     *      Container that now has a removed item.
     * @param item
     *      Item that was removed.
     * @param fromPosition
     *      Position in which the item was removed from within the container.
     */
    void itemRemoved(@NonNull AdapterItemGroup container, @NonNull AdapterItem item, int fromPosition);

    /**
     * Multiple items have been removed from the provided container.
     * @param container
     *      Container that had the removed items.
     * @param fromPosition
     *      Starting position from the first item that was remove.
     * @param size
     *      Number of items that were removed.
     */
    void itemsRemoved(@NonNull AdapterItemGroup container, int fromPosition, int size);
}
