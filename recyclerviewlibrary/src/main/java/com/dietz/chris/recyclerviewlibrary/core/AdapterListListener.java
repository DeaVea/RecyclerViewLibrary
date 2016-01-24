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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 *
 */
public class AdapterListListener implements ListListener {

    public final RecyclerView.Adapter mAdapter;

    public AdapterListListener(@NonNull RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onDatasetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemChanged(int atPosition, AdapterItem payload) {
        mAdapter.notifyItemChanged(atPosition);
    }

    @Override
    public void onItemInserted(int atPosition, AdapterItem payload) {
        mAdapter.notifyItemInserted(atPosition);
    }

    @Override
    public void onItemMoved(int fromPosition, int toPosition, AdapterItem payload) {
        mAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemRangedChanged(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public void onItemRemoved(int position, AdapterItem payload) {
        mAdapter.notifyItemRemoved(position);
    }
}
