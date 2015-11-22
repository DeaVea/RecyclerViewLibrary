package com.dietz.chris.recyclerviewlibrary.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 *
 */
public abstract class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder<? extends AdapterItem>> {

    private static LayoutInflater mInflater;

    private final AdapterList mList;

    public RecyclerAdapter() {
        mList = new AdapterList();
        mList.setListListener(new AdapterListListener(this));
    }

    public void addItem(AdapterItem item) {
        mList.add(item);
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
        int type = getItemViewType(position);
        AdapterItem item = mList.get(position);
        holder.bind(item);
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
