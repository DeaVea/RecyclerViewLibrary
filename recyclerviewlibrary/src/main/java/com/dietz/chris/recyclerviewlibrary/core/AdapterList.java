/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dietz.chris.recyclerviewlibrary.core;

import android.support.annotation.Nullable;

import com.dietz.chris.recyclerviewlibrary.RecyclerItem;

/**
 *
 */
public class AdapterList {

    private final AdapterItemCollection mMainList;

    private final InternalListListener mListListener;

    public AdapterList() {
        mMainList = new AdapterItemCollection(mListListener = new InternalListListener());
    }

    public void setListListener(ListListener listListener) {
        mListListener.setInternalListener(listListener);
    }

    public <K extends RecyclerItem> void addItem(K item) {
        mMainList.addOrUpdate(new AdapterItemGroup<>(item));
    }

    public <K extends RecyclerItem> K findPayloadWithId(String id) {
        //noinspection unchecked  This is supposed to throw a ClassCastException if it isn't what's expected anyway.
        final AdapterItem<K> group = mMainList.findItemWithKey(id);
        return group == null ? null : group.getPayload();
    }

    public AdapterItem findItemWithId(String id) {
        return mMainList.findItemWithKey(id);
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
