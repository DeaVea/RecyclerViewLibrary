package com.dietz.chris.recyclerviewlibrary;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public abstract class ViewHolder<K extends RecyclerItem> extends RecyclerView.ViewHolder {

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
        onBind(item);
    }

    public abstract void onBind(K item);

    public <T extends View> T findView(int id) {
        //noinspection unchecked  It'll throw a ClassCastException anyway so the w
        return (T) itemView.findViewById(id);
    }
}
