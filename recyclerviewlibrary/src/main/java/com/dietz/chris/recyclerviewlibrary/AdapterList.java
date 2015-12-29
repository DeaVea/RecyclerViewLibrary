package com.dietz.chris.recyclerviewlibrary;

import android.support.annotation.Nullable;

/**
 *
 */
class AdapterList {

    private final AdapterItemCollection mMainList;

    private final InternalListListener mListListener;

    public AdapterList() {
        mMainList = new AdapterItemCollection(mListListener = new InternalListListener());
    }

    public void setListListener(ListListener listListener) {
        mListListener.setInternalListener(listListener);
    }

    public <K extends RecyclerItem> void addItem(K item) {
        mMainList.addOrUpdate(new AdapterItem<>(item));
    }

    public <K extends RecyclerGroupItem> void addItem(K item) {
        mMainList.addOrUpdate(new AdapterItemGroup<>(item));
    }

    public <K extends RecyclerItem> void addItemToGroup(K item, RecyclerGroupItem toGroup) {
        final AdapterItem group = mMainList.findItemWithPayload(toGroup);
        if (group != null) {
            group.addOrUpdateItem(new AdapterItem<>(item));
        }
    }

    public <K extends RecyclerGroupItem> void addItemToGroup(K item, RecyclerGroupItem toGroup) {
        final AdapterItem group = mMainList.findItemWithPayload(toGroup);
        if (group != null) {
            group.addOrUpdateItem(new AdapterItemGroup<>(item));
        }
    }

    public <K extends RecyclerItem> int removeItem(K payload) {
        return mMainList.removeItemWithPayload(payload);
    }

    public int size() {
        return mMainList.size();
    }

    public RecyclerItem get(int position) {
        return mMainList.get(position).getPayload();
    }

    public int getType(int position) {
        return mMainList.get(position).getType();
    }

    public void clear() {
        mMainList.clear();
    }

    private static class InternalListListener implements ListListener {

        @Nullable
        private ListListener mInternalListener;

        public InternalListListener() {
            mInternalListener = null;
        }

        public void setInternalListener(@Nullable ListListener listener) {
            mInternalListener = listener;
        }

        @Override
        public void onDatasetChanged() {
            if (mInternalListener != null) {
                mInternalListener.onDatasetChanged();
            }
        }

        @Override
        public void onItemChanged(int atPosition, AdapterItem payload) {
            if (mInternalListener != null) {
                mInternalListener.onItemChanged(atPosition, payload);
            }
        }

        @Override
        public void onItemInserted(int atPosition, AdapterItem payload) {
            if (mInternalListener != null) {
                mInternalListener.onItemInserted(atPosition, payload);
            }
        }

        @Override
        public void onItemMoved(int fromPosition, int toPosition, AdapterItem payload) {
            if (mInternalListener != null) {
                mInternalListener.onItemMoved(fromPosition, toPosition, payload);
            }
        }

        @Override
        public void onItemRangedChanged(int positionStart, int itemCount) {
            if (mInternalListener != null) {
                mInternalListener.onItemRangedChanged(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (mInternalListener != null) {
                mInternalListener.onItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (mInternalListener != null) {
                mInternalListener.onItemRangeRemoved(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRemoved(int position, AdapterItem payload) {
            if (mInternalListener != null) {
                mInternalListener.onItemRemoved(position, payload);
            }
        }
    }
}
