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

import java.util.ArrayList;

/**
 *
 */
public class TestAdapterItemListener implements AdapterListener {

    final ArrayList<AdapterItem> mChangedItems = new ArrayList<>();
    final ArrayList<VisibilityChange> mVisibilityChangeItems = new ArrayList<>();

    public AdapterItem lastChangedItem() {
        return (mChangedItems.isEmpty()) ? null : mChangedItems.get(mChangedItems.size() - 1);
    }

    @Override
    public void itemChanged(@NonNull AdapterItem item) {
        mChangedItems.add(item);
    }

    @Override
    public void itemVisibilityChange(@NonNull AdapterItem item, boolean isVisible, int count) {
        mVisibilityChangeItems.add(new VisibilityChange(isVisible, item));
    }

    @Override
    public void itemAdded(@NonNull AdapterItemGroup container, @NonNull AdapterItem item, int atPosition) {

    }

    @Override
    public void itemsAdded(@NonNull AdapterItemGroup container, int fromPosition, int size) {

    }

    @Override
    public void itemRemoved(@NonNull AdapterItemGroup container, @NonNull AdapterItem item, int fromPosition) {

    }

    @Override
    public void itemsRemoved(@NonNull AdapterItemGroup container, int fromPosition, int size) {

    }

    public static class VisibilityChange {
        public final boolean isVisible;
        public final AdapterItem item;

        public VisibilityChange(boolean visible, AdapterItem i) {
            isVisible = visible;
            item = i;
        }
    }
}
