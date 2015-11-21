package com.dietz.chris.recyclerviewlibrary.recyclerview;

/**
 * AdapterItemGroup is an AdapterItem that handles multiple AdapterItems.
 */
public abstract class AdapterItemGroup extends AdapterItem {
    private final AdapterItemCollection mItems;

    public AdapterItemGroup() {
        mItems = new AdapterItemCollection(new InternalListener());
    }

    @Override
    public final int getItemCount() {
        return (isOpen()) ? 1 + mItems.size() :  1;
    }

    public final void addOrUpdateItem(AdapterItem item) {
        mItems.addOrUpdate(item);
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

        }

        @Override
        public void onItemRemoved(int position, AdapterItem payload) {
            if (isOpen()) {
                notifyItemRemoved(payload, position);
            }
        }
    }
}
