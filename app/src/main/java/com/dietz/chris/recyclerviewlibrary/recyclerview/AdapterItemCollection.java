package com.dietz.chris.recyclerviewlibrary.recyclerview;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 */
class AdapterItemCollection<K extends AdapterItem> {

    private final ArrayList<K> mList;
    private final HashMap<String, K> mMap;
    private final ListListener<K> mListener;
    private final InternalItemListener mItemListener;

    /**
     * Contains the full size of the collection.  This isn't just elements in the list, but the size
     * of the elements that are in that list as well since they could be in groups.
     */
    private int mFullSize;

    public AdapterItemCollection(ListListener<K> listener) {
        mList = new ArrayList<>();
        mMap = new HashMap<>();
        mItemListener = new InternalItemListener();
        mListener = listener;
        mFullSize = 0;
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
    public K get(int position) {
        return mList.get(position);
    }

    /**
     * Removes the item passed in.  This will remove the item that has the underlying identifying key,
     * so if the data structure is different then it will still be removed.
     * @param item
     *      Item to remove
     */
    public void remove(@NonNull K item) {
        K oldItem = mMap.remove(item.getIdentityKey());
        if (oldItem != null) {
            mFullSize -= oldItem.getItemCount();
            int index = mList.indexOf(oldItem);
            mList.remove(oldItem);
            oldItem.unbindListener();
            mListener.onItemRemoved(index, oldItem);
        } // Else this is some weird item that nobody ever put in here.
    }

    /**
     * Add an item or update it if it is already in the collection.
     * @param item
     *      Item to update.
     * @return
     *      New position of the item.
     */
    public int addOrUpdate(@NonNull K item) {
        int position;
        if (mMap.containsKey(item.getIdentityKey())) {
            position = updateInternal(item);
        } else {
            position = addInternal(item);
        }
        return position;
    }

    private int updateInternal(K item) {
        K oldItem = mMap.get(item.getIdentityKey());
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

    private int addInternal(K item) {
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
            mListener.onItemChanged(realIndex, (K) item);
        }

        @Override
        public void itemAdded(@NonNull AdapterItemGroup container, @NonNull AdapterItem item, int atPosition) {
            int realIndex = mList.indexOf(container) + atPosition + 1;
            mListener.onItemInserted(realIndex, (K) item);
        }

        @Override
        public void itemsAdded(@NonNull AdapterItemGroup container, @NonNull int fromPosition, int size) {
            int realIndex = mList.indexOf(container) + fromPosition + 1;
            mListener.onItemRangeInserted(realIndex, size);
        }

        @Override
        public void itemRemoved(@NonNull AdapterItemGroup container, @NonNull AdapterItem item, int fromPosition) {
            int realIndex = mList.indexOf(container) + fromPosition + 1;
            mListener.onItemRemoved(realIndex, (K) item);
        }

        @Override
        public void itemsRemoved(@NonNull AdapterItemGroup container, @NonNull int fromPosition, @NonNull int size) {
            int realIndex = mList.indexOf(container) + fromPosition + 1;
            mListener.onItemRangeRemoved(realIndex, size);
        }
    }

    private static <K extends AdapterItem> boolean equals(K item, K item2) {
        return item.getIdentityKey().equals(item2.getIdentityKey()) && item.hashCode() == item2.hashCode() && item.equals(item2);
    }

    private static void unregisterAllListener(Collection<? extends AdapterItem> items) {
        for (AdapterItem item : items) {
            item.unbindListener();
        }
    }
}
