package com.dietz.chris.recyclerviewlibrary;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 *
 */
public abstract class AdapterItem<K> implements Comparable<AdapterItem> {

    private K mPayload;

    /**
     * Keep this null until there is actually a key.
     */
    private String mDefaultIdentityKey = null;
    private boolean mIsSolid;
    private boolean mIsOpen;
    private AdapterListener mListener;

    public AdapterItem() {
        this(null);
    }

    public AdapterItem(K payload) {
        mIsOpen = true;
        mIsSolid = false;
        mListener = null;
        mPayload = payload;
    }

    /**
     * Sets the payload that is backed by this adapter item.
     * @param payload
     */
    public void setPayload(K payload) {
        mPayload = payload;
        mDefaultIdentityKey = generateDefaultKey();
        notifyListChange();
    }

    /**
     * Returns the paylaod set by this adapter item.
     * @return
     */
    public K getPayload() {
        return mPayload;
    }

    /**
     * Check if the payload provided is in the collection.  Will return false if this doesn't have a payload
     * or if the payload passed in is null.
     */
    public boolean hasPayload(Object payload) {
        return (mPayload != null && payload != null) &&
               mPayload.getClass().equals(payload.getClass()) &&
               ((payload == mPayload || (mPayload.hashCode() == payload.hashCode() && mPayload.equals(payload))));
    }

    /**
     * This returns the number of items that are current in the adapterItem including the adapterItem
     * itself.  By default this returns a 1 since this is the only item here.  Groups should override this
     * to return one + many more.
     * @return
     *      Number of visible items in the adapter item.
     *
     */
    int getItemCount() {
        return 1;
    }

    /**
     * Returns the item at the given position or null if that item is not found.  A value of "0"
     * should return "this" item while a value greater than "0" will return a item further in the collection.
     *
     * @param position
     *      Position to retrieve the item.
     * @return
     *      The item at the given position or null if that item is not found.
     */
    AdapterItem getItem(int position) {
        if (position == 0) {
            return this;
        }
        return null;
    }

    /**
     * Remove an item from the current adapter item.
     * @param item
     *      Item to remove.
     * @return
     *      True if the item was removed or false if it was not successfully removed from the adapter item.
     */
    boolean removeItem(AdapterItem item) {
        return false;
    }

    /**
     * Returns true if the item is found in this adapter item.
     * @param item
     *      item to check.
     * @return
     *      True if the item is in this adapter item or false otherwise.
     */
    boolean containsItem(AdapterItem item) {
        return false;
    }

    /**
     * Bind a listener to this item.
     * @param listener
     *      Adapter listener
     */
    public void bindList(AdapterListener listener) {
        mListener = listener;
    }

    /**
     * Remove the listener from the item.
     */
    public void unbindListener() {
        mListener = null;
    }

    /**
     * If true, then allow this item to be opened or locked as-is.
     * @param allow
     *      Allow the item to be opened or closed.
     *
     */
    public final void allowOpenClose(boolean allow) {
        mIsSolid = !allow;
    }

    /**
     * Returns true if the item has been opened.
     * @return
     *      True if the item is open.
     */
    public final boolean isOpen() {
        return mIsOpen;
    }

    /**
     * Call an open to the call to the adapter item.
     */
    public final void open() {
        if (!mIsSolid && !mIsOpen) {
            onOpen();
            mIsOpen = true;
            notifyListChange();
        }
    }

    /**
     * Perform a close operation on the item.
     */
    public final void close() {
        if (!mIsSolid && mIsOpen) {
            onClose();
            mIsOpen = false;
            notifyListChange();
        }
    }

    /**
     * An open call has been placed on this item.
     */
    public void onOpen() {

    }

    /**
     * A close call has been placed on this item.
     */
    public void onClose() {

    }

    @Nullable
    /* internal */ AdapterListener myListener() {
        return mListener;
    }

    /**
     * Notify that this item has been changed.
     */
    protected void notifyListChange() {
        if (mListener != null) {
            mListener.itemChanged(this);
        }
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
    public String getIdentityKey() {
        if (mDefaultIdentityKey == null) {
            mDefaultIdentityKey = generateDefaultKey();
        }
        return mDefaultIdentityKey;
    }

    /**
     * Type of item that this item represents.
     * @return
     *      Value that this item represents in the list.
     */
    public abstract int getType();

    private String generateDefaultKey() {
        if (mPayload != null) {
            return mPayload.hashCode() + "_" + mPayload.toString();
        }
        return hashCode() + "_" + toString();
    }
}
