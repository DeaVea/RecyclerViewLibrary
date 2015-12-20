package com.dietz.chris.recyclerviewlibrary;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 *
 */
public abstract class RecyclerAdapter<K> extends RecyclerView.Adapter<ViewHolder<? extends AdapterItem>> {

    private static LayoutInflater mInflater;

    private final AdapterList mList;

    public RecyclerAdapter() {
        mList = new AdapterList();
        mList.setListListener(new AdapterListListener(this));
    }

    public void addItem(K item) {
        mList.add(new DefaultAdapterItem<>(item));
    }

    public void removeItem(AdapterItem item) {
        mList.remove(item);
    }

    @Override
    public final ViewHolder<? extends AdapterItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        return onCreateViewHolder(mInflater, parent, viewType);
    }

    public abstract ViewHolder<? extends AdapterItem> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //noinspection unchecked  The item will be the appropriate class if the types are all well and good.
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

    private static class DefaultAdapterItem<K> extends AdapterItem<K> {

        public DefaultAdapterItem(K payload) {
            super(payload);
        }

        @Override
        public int getType() {
            return 0;
        }

        @Override
        public int compareTo(@NonNull AdapterItem another) {
            return another.compareTo(this);
        }
    }
}