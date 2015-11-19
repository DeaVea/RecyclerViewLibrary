package com.dietz.chris.recyclerviewlibrary.recyclerview;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public abstract class ViewHolder<K> extends RecyclerView.ViewHolder {

    private K mBoundItem;

    public ViewHolder(View itemView) {
        super(itemView);
        mBoundItem = null;
    }

    public ViewHolder(LayoutInflater inflater, ViewGroup parent, @LayoutRes int id) {
        this(inflater.inflate(id, parent, false));
    }

    public final K getBoundItem() {
        return mBoundItem;
    }

    public final void bind(K item) {
        mBoundItem = item;
    }

    public abstract void onBind(K item);
}
