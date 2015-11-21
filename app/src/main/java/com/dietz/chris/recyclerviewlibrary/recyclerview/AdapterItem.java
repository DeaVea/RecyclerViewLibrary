package com.dietz.chris.recyclerviewlibrary.recyclerview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 *
 */
public abstract class AdapterItem implements Comparable<AdapterItem> {

    private boolean mIsSolid;
    private boolean mIsOpen;
    private AdapterListener mListener;

    public AdapterItem() {
        mIsOpen = true;
        mIsSolid = false;
        mListener = null;
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

    AdapterItem getItem(int position) {
        if (position == 0) {
            return this;
        }
        return null;
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
    public abstract String getIdentityKey();

    /**
     * Type of item that this item represents.
     * @return
     *      Value that this item represents in the list.
     */
    public abstract int getType();
}
