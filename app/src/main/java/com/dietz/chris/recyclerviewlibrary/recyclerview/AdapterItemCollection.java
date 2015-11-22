package com.dietz.chris.recyclerviewlibrary.recyclerview;

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
        mListener.onItemRangeRemoved(0, size);
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
            mList.remove(oldItem);
            mMap.remove(oldItem.getIdentityKey());
            oldItem.unbindListener();
            mListener.onItemRemoved(index, oldItem);
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

    private int updateInternal(AdapterItem item) {
        AdapterItem oldItem = mMap.get(item.getIdentityKey());
        oldItem.unbindListener();
        item.bindList(mItemListener);

        mMap.put(item.getIdentityKey(), item);

        int oldPosition = mList.indexOf(oldItem);
        mList.remove(oldPosition);

        int newPosition = Utils.getPosition(item, mList);
        mList.add(newPosition, item);

        mFullSize += item.getItemCount() - oldItem.getItemCount();

        if (oldPosition == newPosition) {
            mListener.onItemChanged(newPosition, item);
        } else {
            mListener.onItemMoved(oldPosition, newPosition, item);
        }
        return newPosition;
    }

    private int addInternal(AdapterItem item) {
        item.bindList(mItemListener);
        mMap.put(item.getIdentityKey(), item);
        int position = Utils.getPosition(item, mList);
        if (position > mList.size()) {
            mList.add(item);
            position = mList.size() - 1;
        } else {
            mList.add(position, item);
        }

        mFullSize += item.getItemCount();

        mListener.onItemInserted(position, item);
        return position;
    }

    // All items in this list will be of type "K".  No need to validate it.
    @SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
    private class InternalItemListener implements AdapterListener {

        @Override
        public void itemChanged(@NonNull AdapterItem item) {
            int realIndex = mList.indexOf(item);
            mListener.onItemChanged(realIndex, item);
        }

        @Override
        public void itemAdded(@NonNull AdapterItemGroup container, @NonNull AdapterItem item, int atPosition) {
            ++mFullSize;
            int realIndex = mList.indexOf(container) + atPosition + 1;
            mListener.onItemInserted(realIndex, item);
        }

        @Override
        public void itemsAdded(@NonNull AdapterItemGroup container, int fromPosition, int size) {
            mFullSize += size;
            int realIndex = mList.indexOf(container) + fromPosition + 1;
            mListener.onItemRangeInserted(realIndex, size);
        }

        @Override
        public void itemRemoved(@NonNull AdapterItemGroup container, @NonNull AdapterItem item, int fromPosition) {
            --mFullSize;
            int realIndex = mList.indexOf(container) + fromPosition + 1;
            mListener.onItemRemoved(realIndex, item);
        }

        @Override
        public void itemsRemoved(@NonNull AdapterItemGroup container, int fromPosition, int size) {
            mFullSize -= size;
            int realIndex = mList.indexOf(container) + fromPosition + 1;
            mListener.onItemRangeRemoved(realIndex, size);
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
