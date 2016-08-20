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

import com.dietz.chris.recyclerviewlibrary.RecyclerItem;

import java.util.Collection;

/**
 * AdapterItemGroup is an AdapterItem that handles multiple AdapterItems.
 */
public class AdapterItemGroup<K extends RecyclerItem> extends AdapterItem<K> {
    private final AdapterItemCollection mItems;

    public AdapterItemGroup(K payload) {
        super(payload);
        mItems = new AdapterItemCollection(new InternalListener());
    }

    @Override
    public final <H extends RecyclerItem> AdapterItem getItemWithPayload(H payload) {
        return Utils.itemsEqual(getPayload(), payload) ? this : mItems.findItemWithPayload(payload);
    }

    @Override
    public final AdapterItem getItemWithIdentityKey(String identityKey) {
        AdapterItem returnItem = super.getItemWithIdentityKey(identityKey);
        return returnItem == null ? mItems.findItemWithKey(identityKey) : null;
    }

    @Override
    public final <H extends RecyclerItem> boolean hasPayload(H payload) {
        return super.hasPayload(payload) || mItems.containsPayload(payload);
    }

    @Override
    public final <H extends RecyclerItem> int removeItemWithPayload(H payload) {
        return mItems.removeItemWithPayload(payload);
    }

    @Override
    public final int getItemCount() {
        int baseCount = super.getItemCount();
        if (baseCount > 0) {
            return (isOpen()) ? baseCount + mItems.size() : baseCount;
        } else {
            return baseCount;
        }
    }

    @Override
    public final boolean addOrUpdateItem(AdapterItem item) {
        mItems.addOrUpdate(item);
        return true;
    }

    @Override
    public final boolean addOrUpdateItems(Collection<AdapterItem> items) {
        for (AdapterItem item : items) {
            addOrUpdateItem(item);
        }
        return true;
    }

    @Override
    public final boolean removeItem(AdapterItem item) {
        return mItems.remove(item);
    }

    @Override
    public AdapterItem getItem(int position) {
        if (position == 0) {
            return this;
        }

        --position;
        if (position < mItems.size()) {
            return mItems.get(position);
        }
        return null;
    }

    @Override
    public final void onOpen() {
        notifyItemsAdded(0, mItems.size());
    }

    @Override
    public final void onClose() {
        notifyItemsRemoved(0, mItems.size());
    }

    @Override
    public final boolean containsItem(AdapterItem item) {
        return mItems.contains(item);
    }

    @Override
    public <T extends RecyclerItem> void filter(Filter<T> filter, @NonNull Class<T> ofClass) {
        super.filter(filter, ofClass);
        mItems.applyFilter(filter, ofClass);
    }

    protected final void notifyItemsAdded(int startingAt, int size) {
        final AdapterListener listener = myListener();
        if (listener != null) {
            listener.itemsAdded(this, startingAt, size);
        }
    }

    protected final void notifyItemsRemoved(int startingAt, int size) {
        final AdapterListener listener = myListener();
        if (listener != null) {
            listener.itemsRemoved(this, startingAt, size);
        }
    }

    protected final void notifyItemChanged(AdapterItem item) {
        final AdapterListener listener = myListener();
        if (listener != null) {
            listener.itemChanged(item);
        }
    }

    protected final void notifyItemAdded(AdapterItem itemAdded, int atPosition) {
        final AdapterListener listener = myListener();
        if (listener != null) {
            listener.itemAdded(this, itemAdded, atPosition);
        }
    }

    protected final void notifyItemRemoved(AdapterItem itemRemoved, int fromPosition) {
        final AdapterListener listener = myListener();
        if (listener != null) {
            listener.itemRemoved(this, itemRemoved, fromPosition);
        }
    }

    private class InternalListener implements ListListener {

        @Override
        public void onDatasetChanged() {
        }

        @Override
        public void onItemChanged(int atPosition, AdapterItem payload) {
            if (isOpen()) {
                notifyItemChanged(payload);
            }
        }

        @Override
        public void onItemInserted(int atPosition, AdapterItem payload) {
            if (isOpen()) {
                notifyItemAdded(payload, atPosition);
            }
        }

        @Override
        public void onItemMoved(int fromPosition, int toPosition, AdapterItem payload) {

        }

        @Override
        public void onItemRangedChanged(int positionStart, int itemCount) {

        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (isOpen()) {
                notifyItemsAdded(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (isOpen()) {
                notifyItemsRemoved(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRemoved(int position, AdapterItem payload) {
            if (isOpen()) {
                notifyItemRemoved(payload, position);
            }
        }
    }
}
