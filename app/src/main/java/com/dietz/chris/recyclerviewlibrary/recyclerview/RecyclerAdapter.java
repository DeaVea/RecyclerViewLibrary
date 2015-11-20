package com.dietz.chris.recyclerviewlibrary.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public abstract class RecyclerAdapter<K extends AdapterItem> extends RecyclerView.Adapter<ViewHolder<K>> {

    private static LayoutInflater mInflater;

    private final AdapterList<K> mList;

    public RecyclerAdapter() {
        mList = new AdapterList<>();
        mList.setListListener(new AdapterListListener<K>(this));
    }

    public void addItem(K item) {
        mList.add(item);
    }

    public void removeItem(K item) {
        mList.remove(item);
    }

    @Override
    public final ViewHolder<K> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        return onCreateViewHolder(mInflater, parent, viewType);
    }

    public abstract ViewHolder<K> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(ViewHolder<K> holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return mList.getType(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
