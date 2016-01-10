package com.dietz.chris.recyclerviewlibrary.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dietz.chris.recyclerviewlibrary.RecyclerItem;

/**
 *
 */
public class AdapterItem<K extends RecyclerItem> implements Comparable<AdapterItem> {

    private K mPayload;

    /**
     * Keep this null until there is actually a key.
     */
    private String mDefaultIdentityKey = null;
    private boolean mIsSolid;
    private boolean mIsOpen;
    private AdapterListener mListener;

    public AdapterItem(K payload) {
        if (payload == null) {
            throw new IllegalStateException("Payload must not be null.");
        }
        mIsOpen = true;
        mIsSolid = false;
        mListener = null;
        mPayload = payload;
    }

    /**
     * This adds a payload to the Adapter Item.  This is different than {@link #setPayload} in that
     * rather this item is left untouched.  Instead, a new AdapterItem will be added to the collection.
     *
     * @param payload
     *      Payload to add.
     */
    public <T extends RecyclerItem> boolean addOrUpdatePayload(T payload) {
        return addOrUpdateItem(new AdapterItemGroup<>(payload));
    }

    /**
     * Sets the payload that is backed by this adapter item.
     * @param payload
     *      Item that is going to represent this adapter item
     */
    public void setPayload(K payload) {
        if (payload == null) {
            throw new IllegalStateException("Payload must not be null.");
        }
        mPayload = payload;
        mDefaultIdentityKey = generateDefaultKey();
        notifyListChange();
    }

    /**
     * Returns the item with the given payload.
     * @param payload
     *      Payload to check
     * @return
     *      First item found with the payload or null if it was not found.
     */
    <H extends RecyclerItem> AdapterItem getItemWithPayload(H payload) {
        if (Utils.itemsEqual(mPayload, payload)) {
            return this;
        }
        return null;
    }

    /**
     * Returns the item that shares the identity key or null if the item was not found.
     * @param item
     *      Item key to search for.
     * @return
     *      Null if the item was not found.
     */
    AdapterItem getItemWithIdentityKey(String item) {
        return getIdentityKey().equals(item) ? this : null;
    }

    /**
     * Returns the paylaod set by this adapter item.
     * @return
     *      Item that is backing this adapter item
     */
    public K getPayload() {
        return mPayload;
    }

    /**
     * Check if the payload provided is in the collection.  Will return false if this doesn't have a payload
     * or if the payload passed in is null.
     */
    public <H extends RecyclerItem> boolean hasPayload(H payload) {
        return payload != null && mPayload.getIdentityKey().equals(payload.getIdentityKey());
    }

    /**
     * If this is a group, this will remove the item that contains the payload from the group.
     * @param payload
     *      Payload to remove from the group.
     * @return
     *      Number of items that were found and removed.
     */
    <H extends RecyclerItem> int removeItemWithPayload(H payload) {
        return 0;
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
     * Add an item from the current adapter item.
     * @param item
     *      Item to remove.
     * @return
     *      True if the item was added or false if it was not successfully added from the adapter item.
     */
    boolean addOrUpdateItem(AdapterItem item) {
        return false;
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
    public int getType() {
        return mPayload.getType();
    }

    private String generateDefaultKey() {
        if (mPayload != null) {
            return (mPayload.getIdentityKey() != null) ? mPayload.getIdentityKey() : mPayload.hashCode() + "_" + mPayload.toString();
        }
        return hashCode() + "_" + toString();
    }

    @Override
    public int compareTo(@NonNull AdapterItem another) {
        // TODO: This is really weird.
        return getPayload().compareTo(another.getPayload());
    }
}
