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

/**
 *
 */
interface ListListener {

    void onDatasetChanged();

    void onItemChanged(int atPosition, AdapterItem payload);

    void onItemInserted(int atPosition, AdapterItem payload);

    void onItemMoved(int fromPosition, int toPosition, AdapterItem payload);

    void onItemRangedChanged(int positionStart, int itemCount);

    void onItemRangeInserted(int positionStart, int itemCount);

    void onItemRangeRemoved(int positionStart, int itemCount);

    void onItemRemoved(int position, AdapterItem payload);
}
