package com.dietz.chris.recyclerviewlibrary.recyclerview;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
class DuelCollection<K extends AdapterItem> {

    private final ArrayList<K> mList;
    private final HashMap<String, K> mMap;
    private final ListListener<K> mListener;

    public DuelCollection(ListListener<K> listener) {
        mList = new ArrayList<>();
        mMap = new HashMap<>();
        mListener = listener;
    }

    /**
     * Current size of the collection.
     * @return
     *      Current size of the entire collection.
     */
    public int size() {
        return mList.size();
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
        mMap.put(item.getIdentityKey(), item);

        int oldPosition = mList.indexOf(oldItem);
        mList.remove(oldPosition);

        int newPosition = Utils.getPosition(item, mList);
        mList.add(newPosition, item);

        if (oldPosition == newPosition) {
            mListener.onItemChanged(newPosition, item);
        } else {
            mListener.onItemMoved(oldPosition, newPosition, item);
        }
        return newPosition;
    }

    private int addInternal(K item) {
        mMap.put(item.getIdentityKey(), item);
        int position = Utils.getPosition(item, mList);
        if (position > mList.size()) {
            mList.add(item);
            position = mList.size() - 1;
        } else {
            mList.add(position, item);
        }
        mListener.onItemInserted(position, item);
        return position;
    }

    private static <K extends AdapterItem> boolean equals(K item, K item2) {
        return item.getIdentityKey().equals(item2.getIdentityKey()) && item.hashCode() == item2.hashCode() && item.equals(item2);
    }
}
