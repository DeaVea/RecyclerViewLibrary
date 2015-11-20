package com.dietz.chris.recyclerviewlibrary.recyclerview;

import android.support.annotation.Nullable;

/**
 *
 */
public class AdapterList<K extends AdapterItem> {

    private final AdapterItemCollection<K> mMainList;

    private final InternalListListener<K> mListListener;

    public AdapterList() {
        mMainList = new AdapterItemCollection<>(mListListener = new InternalListListener<>());
    }

    public void setListListener(ListListener<K> listListener) {
        mListListener.setInternalListener(listListener);
    }

    public void add(K item) {
        mMainList.addOrUpdate(item);
    }

    public void remove(K item) {
        mMainList.remove(item);
    }

    public int size() {
        return mMainList.size();
    }

    public K get(int position) {
        return mMainList.get(position);
    }

    public int getType(int position) {
        return mMainList.get(position).getType();
    }

    public void clear() {
        mMainList.clear();
    }

    private static class InternalListListener<K extends AdapterItem> implements ListListener<K> {

        @Nullable
        private ListListener<K> mInternalListener;

        public InternalListListener() {
            mInternalListener = null;
        }

        public void setInternalListener(@Nullable ListListener<K> listener) {
            mInternalListener = listener;
        }

        @Override
        public void onDatasetChanged() {
            if (mInternalListener != null) {
                mInternalListener.onDatasetChanged();
            }
        }

        @Override
        public void onItemChanged(int atPosition, K payload) {
            if (mInternalListener != null) {
                mInternalListener.onItemChanged(atPosition, payload);
            }
        }

        @Override
        public void onItemInserted(int atPosition, K payload) {
            if (mInternalListener != null) {
                mInternalListener.onItemInserted(atPosition, payload);
            }
        }

        @Override
        public void onItemMoved(int fromPosition, int toPosition, K payload) {
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
        public void onItemRemoved(int position, K payload) {
            if (mInternalListener != null) {
                mInternalListener.onItemRemoved(position, payload);
            }
        }
    }
}
