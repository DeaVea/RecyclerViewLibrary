package com.dietz.chris.recyclerviewlibrary;

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
