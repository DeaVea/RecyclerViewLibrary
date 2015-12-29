package com.dietz.chris.recyclerviewlibrary;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 *
 */
public abstract class RecyclerAdapter<G extends RecyclerGroupItem, K extends RecyclerItem> extends RecyclerView.Adapter<ViewHolder<? extends RecyclerItem>> {

    private static LayoutInflater mInflater;

    private final AdapterList mList;

    public RecyclerAdapter() {
        mList = new AdapterList();
        mList.setListListener(new AdapterListListener(this));
    }

    public void addItem(K item) {
        mList.addItem(item);
    }

    public void addItem(G item) {
        mList.addItem(item);
    }

    public void removeItem(G item) {
        mList.removeItem(item);
    }

    public void removeItem(K item) {
        mList.removeItem(item);
    }

    public void addItemToGroup(G group, K item) {
        mList.addItemToGroup(item, group);
    }

    public void addItemToGroup(G group, G otherGroup) {
        mList.addItemToGroup(otherGroup, group);
    }

    @Override
    public final ViewHolder<? extends RecyclerItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        return onCreateViewHolder(mInflater, parent, viewType);
    }

    public abstract ViewHolder<? extends RecyclerItem> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

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
}
