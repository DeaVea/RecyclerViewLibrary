package com.dietz.chris.recyclerviewlibrary;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 */
class AdapterItemCollection {

    private final ArrayList<AdapterItem> mList;
    private final HashMap<String, AdapterItem> mMap;
    private final ListListener mListener;
    private final InternalItemListener mItemListener;

    /**
     * Contains the full size of the collection.  This isn't just elements in the list, but the size
     * of the elements that are in that list as well since they could be in groups.
     */
    private int mFullSize;

    public AdapterItemCollection(ListListener listener) {
        mList = new ArrayList<>();
        mMap = new HashMap<>();
        mItemListener = new InternalItemListener();
        mListener = listener;
        mFullSize = 0;
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
     * @param payload
     *      Payload item to check
     * @return
     *      First item found that has the payload or null if it does not.
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
     * Finds all the items that are in this list that contains the payload and removes it.
     * @param payload
     *      payload item to look for.
     * @return
     *      Number of items that were removed.
     */
    public <K extends RecyclerItem> int removeItemWithPayload(K payload) {
        int removedItems = 0;
        ArrayList<AdapterItem> itemsToRemove = new ArrayList<>();
        // TODO: This sucks.  Fix it.
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
     * @param item
     *      Item to check.
     * @return
     *      True if the item is in the collection or false otherwise.
     */
    public boolean contains(AdapterItem item) {
        if (item == null) {
            return false;
        }
        if (mMap.containsKey(item.getIdentityKey())) {
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
     * @return
     *      Current size of the entire collection.
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
        mMap.clear();
        mFullSize = 0;
        onItemRangeRemoved(0, size);
    }

    /**
     * Gets the item at the given position.
     * @param position
     *      Position to retrieve the item.
     * @return
     *      Item contained at the given position.
     */
    // If the returning call is not the right type, then something went wrong anyway so it should throw an exception.
    @SuppressWarnings("unchecked")
    public <K extends AdapterItem> K get(int position) {
        int remaining = position;
        int max = mList.size();
        for (int i = 0; i < max; i++) {
            AdapterItem item = mList.get(i);
            if (remaining == 0) {
                return (K) item;
            } else if (remaining < item.getItemCount()){
                return (K) item.getItem(remaining);
            }
            remaining -= item.getItemCount();
        }
        return null;
    }

    /**
     * Removes the item passed in.  This will remove the item that has the underlying identifying AdapterItem key,
     * so if the data structure is different then it will still be removed.
     * @param item
     *      Item to remove
     * @return
     *      True if the item was removed or false otherwise.
     */
    public boolean remove(@NonNull AdapterItem item) {
        return removeItemFromHere(item) | removeItemFromCollection(item);
    }

    /**
     * Add an item or update it if it is already in the collection.
     * @param item
     *      Item to update.
     * @return
     *      New position of the item.
     */
    public int addOrUpdate(@NonNull AdapterItem item) {
        int position;
        if (mMap.containsKey(item.getIdentityKey())) {
            position = updateInternal(item);
        } else {
            position = addInternal(item);
        }
        return position;
    }

    /**
     * Removes a collection of items from the internal list.
     * @param items
     *      Items to remove.
     * @return
     *      Number of items that were actaully removed.
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
     * @param item
     *      Item to remove.  It must be in this collection.
     */
    private boolean removeItemFromHere(AdapterItem item) {
        final AdapterItem oldItem = mMap.remove(item.getIdentityKey());
        if (oldItem != null) {
            int numberOfItems = oldItem.getItemCount();
            mFullSize -= numberOfItems;
            int index = mList.indexOf(oldItem);
            final int indexInFull = Utils.adjustPositionForItems(index, mList);
            mList.remove(oldItem);
            mMap.remove(oldItem.getIdentityKey());
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
     * @param item
     *      Item to remove.  Does not have to be in this collection itself.
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
     * @param item
     *      Item to update.
     * @return
     *      New position of the item in the entire list.
     */
    private int updateInternal(AdapterItem item) {
        AdapterItem oldItem = mMap.get(item.getIdentityKey());
        oldItem.unbindListener();
        item.bindList(mItemListener);

        mMap.put(item.getIdentityKey(), item);

        int oldPositionInMyList = mList.indexOf(oldItem);
        int oldPositionInOverallList = Utils.adjustPositionForItems(oldPositionInMyList, mList);
        mList.remove(oldPositionInMyList);

        int newPositionInMyList = Utils.getPositionInList(item, mList);
        int newPositionInOverallList = Utils.adjustPositionForItems(newPositionInMyList, mList);
        if (newPositionInMyList >= mList.size()) {
            mList.add(item);
        } else {
            mList.add(newPositionInMyList, item);
        }

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
     * @param item
     *      Item to add
     * @return
     *      New position in the overall list that contains the item.
     */
    private int addInternal(AdapterItem item) {
        item.bindList(mItemListener);
        mMap.put(item.getIdentityKey(), item);
        int positionInMyList = Utils.getPositionInList(item, mList);
        int positionInOverallList = Utils.adjustPositionForItems(positionInMyList, mList);

        if (positionInMyList >= mList.size()) {
            mList.add(item);
        } else {
            mList.add(positionInMyList, item);
        }

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

    // All items in this list will be of type "K".  No need to validate it.
    @SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
    private class InternalItemListener implements AdapterListener {

        @Override
        public void itemChanged(@NonNull AdapterItem item) {
            int realIndex = Utils.getPosition(item, mList);
            mListener.onItemChanged(realIndex, item);
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

    private static boolean equals(AdapterItem item, AdapterItem item2) {
        return item.getIdentityKey().equals(item2.getIdentityKey()) && item.hashCode() == item2.hashCode() && item.equals(item2);
    }

    private static void unregisterAllListener(Collection<? extends AdapterItem> items) {
        for (AdapterItem item : items) {
            item.unbindListener();
        }
    }
}
