package com.dietz.chris.recyclerviewlibrary.recyclerview;

import android.support.annotation.NonNull;

/**
 *
 */
public abstract class AdapterItem implements Comparable<AdapterItem> {

    private int mPositionInList = -1;

    /**
     * A method that the adapter list can use to keep track of where this item is placed.
     * @return
     *      Current position in the list.  A value of less than 0 should means this item is not in the list.
     */
    /* internal */ int getPositionInList() {
        return mPositionInList;
    }

    /**
     * Set the item's current position in this list.
     * @param position
     *      The current position in this list.
     */
    /* internal */ void setPositionInList(int position) {
        mPositionInList = position;
    }

    /**
     * This is the key to identify the object if the contents represent the same datapoints.  This is usually
     * used to by the {@link AdapterList} to determine if an object should be added, updated, or left alone.
     *
     * For example,
     * AdapterItem obj1 = getAdapterObject1();
     * AdapterItem obj2 = getAdapterObject2();
     *
     * if (obj1.getKey().equals(ob2.getKey()) {
     *     if (obj1.hashCode() != obj2.hashCode()) {
     *       // The data that was used to created has been changed so need to be updated.
     *     } // else The data is equal.  No need to update.
     * } // else The two objects represent two different items.
     *
     * @return
     *      A unique key used to identify the object.
     */
    @NonNull
    public abstract String getIdentityKey();

    /**
     * Type of item that this item represents.
     * @return
     *      Value that this item represents in the list.
     */
    public abstract int getType();
}
