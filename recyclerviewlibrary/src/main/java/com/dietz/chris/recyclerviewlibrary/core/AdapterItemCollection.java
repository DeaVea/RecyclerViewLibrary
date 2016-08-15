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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 */
class AdapterItemCollection {
    private final HashArrayList<AdapterItem> mList;
    private final ListListener mListener;
    private final InternalItemListener mItemListener;

    /**
     * Contains the full size of the collection.  This isn't just elements in the list, but the size
     * of the elements that are in that list as well since they could be in groups.
     */
    private int mFullSize;

    public AdapterItemCollection(ListListener listener) {
        mList = new HashArrayList<>();
        mItemListener = new InternalItemListener();
        mListener = listener;
        mFullSize = 0;
    }

    /**
     * Apply a filter to only show the items that are in this.
     *
     * @param filter
     *         Filter to apply.
     * @param classType
     *         Type of object this is filtering out.
     */
    public <K extends RecyclerItem> void applyFilter(Filter<K> filter, Class<K> classType) {
        for (AdapterItem item : mList) {
            item.filter(filter, classType);
        }
    }

    /**
     * Returns if the payload is in the list.
     */
    public <K extends RecyclerItem> boolean containsPayload(K payload) {
        for (AdapterItem items : mList) {
            if (items.hasPayload(payload)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the first item with the given payload.
     *
     * @param payload
     *         Payload item to check
     *
     * @return First item found that has the payload or null if it does not.
     */
    public <K extends RecyclerItem> AdapterItem findItemWithPayload(K payload) {
        AdapterItem returnItem;
        for (AdapterItem item : mList) {
            if ((returnItem = item.getItemWithPayload(payload)) != null) {
                return returnItem;
            }
        }
        return null;
    }

    /**
     * Finds the first item with the given key or null if it is not found.
     *
     * @param key
     *         Key to search for.
     *
     * @return AdapterItem found with the given key or null if it is not in the list.
     */
    public AdapterItem findItemWithKey(String key) {
        AdapterItem returnItem = mList.get(key);
        if (returnItem == null) {
            for (AdapterItem item : mList) {
                if ((returnItem = item.getItemWithIdentityKey(key)) != null) {
                    return returnItem;
                }
            }
        }
        return returnItem;
    }

    /**
     * Finds all the items that are in this list that contains the payload and removes it.
     *
     * @param payload
     *         payload item to look for.
     *
     * @return Number of items that were removed.
     */
    public <K extends RecyclerItem> int removeItemWithPayload(K payload) {
        int removedItems = 0;
        ArrayList<AdapterItem> itemsToRemove = new ArrayList<>();
        for (AdapterItem item : mList) {
            int itemsRemoved = item.removeItemWithPayload(payload);
            if (itemsRemoved == 0) {
                if (Utils.itemsEqual(item.getPayload(), payload)) {
                    itemsToRemove.add(item);
                    removedItems += item.getItemCount();
                }
            } else {
                removedItems += itemsRemoved;
            }
        }
        removeItemsFromHere(itemsToRemove);
        return removedItems;
    }

    /**
     * Returns true if the collection contains the given item.
     *
     * @param item
     *         Item to check.
     *
     * @return True if the item is in the collection or false otherwise.
     */
    public boolean contains(AdapterItem item) {
        if (mList.contains(item)) {
            return true;
        }
        for (AdapterItem internalItem : mList) {
            if (internalItem.containsItem(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Current size of the collection.
     *
     * @return Current size of the entire collection.
     */
    public int size() {
        return mFullSize;
    }

    /**
     * Removes all items from the collection
     */
    public final void clear() {
        int size = mFullSize;
        unregisterAllListener(mList);
        mList.clear();
        mFullSize = 0;
        onItemRangeRemoved(0, size);
    }

    /**
     * Gets the item at the given position.
     *
     * @param position
     *         Position to retrieve the item.
     *
     * @return Item contained at the given position.
     */
    // If the returning call is not the right type, then something went wrong anyway so it should throw an exception.
    @SuppressWarnings("unchecked")
    public <K extends AdapterItem> K get(int position) {
        int remaining = position;
        int max = mList.size();
        for (int i = 0; i < max; i++) {
            AdapterItem item = mList.get(i);
            if (remaining < item.getItemCount()) {
                return (K) item.getItem(remaining);
            }
            remaining -= item.getItemCount();
        }
        return null;
    }

    /**
     * Removes the item passed in.  This will remove the item that has the underlying identifying AdapterItem key,
     * so if the data structure is different then it will still be removed.
     *
     * @param item
     *         Item to remove
     *
     * @return True if the item was removed or false otherwise.
     */
    public boolean remove(@NonNull AdapterItem item) {
        return removeItemFromHere(item) | removeItemFromCollection(item);
    }

    /**
     * Add an item or update it if it is already in the collection.
     *
     * @param item
     *         Item to update.
     *
     * @return New position of the item.
     */
    public int addOrUpdate(@NonNull AdapterItem item) {
        int position;
        if (mList.contains(item)) {
            position = updateInternal(item);
        } else {
            position = addInternal(item);
        }
        return position;
    }

    /**
     * Retrieves all the adapter items in this collection that are contained within this collection.
     * This will be determined based off their identity key.
     * <p/>
     * This will only return the items that are contained in this collection.  If the items are not
     * in this collection then they will be left out, so it's possible the returned collection will
     * be less than the size of the items given.
     *
     * @param items
     *         Payloads to retrieve in this collection.
     *
     * @return All the items that are in this collection that
     */
    /* internal */ Collection<AdapterItem> getItemsWithPayloads(Collection<? extends RecyclerItem> items) {
        HashSet<AdapterItem> returnItems = new HashSet<>(items.size());
        for (RecyclerItem item : items) {
            AdapterItem containedItem = mList.get(item.getIdentityKey());
            if (containedItem != null) {
                returnItems.add(containedItem);
            }
        }
        return returnItems;
    }

    /**
     * Retrieve all the adapter items in this collection that are not contained within this collection.
     * This will be determined based off their identity key.
     * <p/>
     * This will only return the items that are contained in this collection which do not have the payloads
     * provided.
     *
     * @param items
     *         Payloads to exclude from the collection.
     *
     * @return All the items that don't have this provided payloads.
     */
    /* internal */ Collection<AdapterItem> getItemsExcludingPayloads(Collection<? extends RecyclerItem> items) {
        HashSet<String> keys = new HashSet<>(items.size());
        for (RecyclerItem item : items) {
            keys.add(item.getIdentityKey());
        }

        HashSet<AdapterItem> returnItems = new HashSet<>(mList.size() - items.size());
        for (AdapterItem item : mList) {
            if (!keys.contains(item.getIdentityKey())) {
                returnItems.add(item);
            }
        }
        return returnItems;
    }

    /**
     * Removes a collection of items from the internal list.
     *
     * @param items
     *         Items to remove.
     *
     * @return Number of items that were actaully removed.
     */
    private int removeItemsFromHere(Collection<AdapterItem> items) {
        int itemsRemoved = 0;
        for (AdapterItem item : items) {
            itemsRemoved += (removeItemFromHere(item)) ? item.getItemCount() : 0;
        }

        return itemsRemoved;
    }

    /**
     * Remove an item that's contained in this collection.
     *
     * @param item
     *         Item to remove.  It must be in this collection.
     */
    private boolean removeItemFromHere(AdapterItem item) {
        final int index = mList.indexOf(item);
        final AdapterItem oldItem = mList.remove(item.getIdentityKey());
        if (oldItem != null) {
            int numberOfItems = oldItem.getItemCount();
            mFullSize -= numberOfItems;

            final int indexInFull = Utils.adjustPositionForItems(index, mList);
            oldItem.unbindListener();

            if (numberOfItems == 1) {
                onItemRemoved(indexInFull, oldItem);
            } else {
                onItemRangeRemoved(indexInFull, numberOfItems);
            }
            return true;
        }
        return false;
    }

    /**
     * Remove an item that's contained in other items.
     *
     * @param item
     *         Item to remove.  Does not have to be in this collection itself.
     */
    private boolean removeItemFromCollection(AdapterItem item) {
        boolean removed = false;
        for (AdapterItem internalItem : mList) {
            removed |= internalItem.removeItem(item);
        }
        return removed;
    }

    /**
     * Updates an item that is in the collection that contains the same identity key as the one provided.
     *
     * @param item
     *         Item to update.
     *
     * @return New position of the item in the entire list.
     */
    private int updateInternal(AdapterItem item) {
        AdapterItem oldItem = mList.getReal(item);
        oldItem.unbindListener();

        item.bindList(mItemListener);

        int oldPositionInMyList = mList.indexOf(oldItem);
        int oldPositionInOverallList = Utils.adjustPositionForItems(oldPositionInMyList, mList);

        mList.remove(oldPositionInMyList);

        int newPositionInMyList = Utils.getPositionInList(item, mList);
        int newPositionInOverallList = Utils.adjustPositionForItems(newPositionInMyList, mList);

        mList.safeAdd(newPositionInMyList, item);

        mFullSize += item.getItemCount() - oldItem.getItemCount();

        if (newPositionInOverallList == oldPositionInOverallList) {
            onItemChanged(newPositionInMyList, item);
        } else {
            onItemMoved(oldPositionInOverallList, newPositionInOverallList, item);
        }
        return newPositionInOverallList;
    }

    /**
     * Adds an item in the current list.  This does not check if the key already exists.  It just assumes it does not.
     *
     * @param item
     *         Item to add
     *
     * @return New position in the overall list that contains the item.
     */
    private int addInternal(AdapterItem item) {
        item.bindList(mItemListener);

        int positionInMyList = Utils.getPositionInList(item, mList);
        int positionInOverallList = Utils.adjustPositionForItems(positionInMyList, mList);

        mList.safeAdd(positionInMyList, item);

        mFullSize += item.getItemCount();

        onItemInserted(positionInOverallList, item);
        return positionInOverallList;
    }

    private void onItemRemoved(int indexOfItem, AdapterItem oldIitem) {
        if (mListener != null) {
            mListener.onItemRemoved(indexOfItem, oldIitem);
        }
    }

    private void onItemRangeRemoved(int indexOfItem, int numberOfItems) {
        if (mListener != null) {
            mListener.onItemRangeRemoved(indexOfItem, numberOfItems);
        }
    }

    private void onItemChanged(int position, AdapterItem item) {
        if (mListener != null) {
            mListener.onItemChanged(position, item);
        }
    }

    private void onItemMoved(int oldPosition, int newPosition, AdapterItem item) {
        if (mListener != null) {
            mListener.onItemMoved(oldPosition, newPosition, item);
        }
    }

    private void onItemInserted(int position, AdapterItem item) {
        if (mListener != null) {
            mListener.onItemInserted(position, item);
        }
    }

    private void onItemRangeInserted(int position, int size) {
        if (mListener != null) {
            mListener.onItemRangeInserted(position, size);
        }
    }

    private class InternalItemListener implements AdapterListener {

        @Override
        public void itemChanged(@NonNull AdapterItem item) {
            if (!item.isHidden()) {
                int realIndex = Utils.getPosition(item, mList);
                onItemChanged(realIndex, item);
            }
        }

        @Override
        public void itemVisibilityChange(@NonNull AdapterItem item, boolean isVisible, int itemsCount) {
            int realIndex = Utils.getPosition(item, mList);
            if (isVisible) {
                mFullSize += itemsCount;
                if (itemsCount > 1) {
                    onItemRangeInserted(realIndex, itemsCount);
                } else {
                    onItemInserted(realIndex, item);
                }
            } else {
                mFullSize -= itemsCount;
                if (itemsCount > 1) {
                    onItemRangeRemoved(realIndex, itemsCount);
                } else {
                    onItemRemoved(realIndex, item);
                }
            }
        }

        @Override
        public void itemAdded(@NonNull AdapterItemGroup container, @NonNull AdapterItem item, int atPosition) {
            ++mFullSize;
            int realIndex = Utils.getPosition(container, mList) + atPosition + 1;
            onItemInserted(realIndex, item);
        }

        @Override
        public void itemsAdded(@NonNull AdapterItemGroup container, int fromPosition, int size) {
            mFullSize += size;
            int realIndex = Utils.getPosition(container, mList) + fromPosition + 1;
            onItemRangeInserted(realIndex, size);
        }

        @Override
        public void itemRemoved(@NonNull AdapterItemGroup container, @NonNull AdapterItem item, int fromPosition) {
            --mFullSize;
            int realIndex = Utils.getPosition(container, mList) + fromPosition + 1;
            onItemRemoved(realIndex, item);
        }

        @Override
        public void itemsRemoved(@NonNull AdapterItemGroup container, int fromPosition, int size) {
            mFullSize -= size;
            int realIndex = Utils.getPosition(container, mList) + fromPosition + 1;
            onItemRangeRemoved(realIndex, size);
        }
    }

    private static void unregisterAllListener(Collection<? extends AdapterItem> items) {
        for (AdapterItem item : items) {
            item.unbindListener();
        }
    }
}
