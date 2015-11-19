package com.dietz.chris.recyclerviewlibrary.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public class RecyclerAdapter<K> extends RecyclerView.Adapter<ViewHolder<K>> {

    @Override
    public ViewHolder<K> onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder<K> holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
