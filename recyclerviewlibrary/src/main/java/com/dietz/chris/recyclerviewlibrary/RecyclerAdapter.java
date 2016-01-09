package com.dietz.chris.recyclerviewlibrary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dietz.chris.recyclerviewlibrary.core.AdapterList;
import com.dietz.chris.recyclerviewlibrary.core.AdapterListListener;

/**
 *
 */
public class RecyclerAdapter<G extends RecyclerGroupItem, K extends RecyclerItem> extends RecyclerView.Adapter<ViewHolder<? extends RecyclerItem>> {

    private static LayoutInflater mInflater;

    private final AdapterList mList;
    private ViewHolderFactory mHolderFactory;

    public RecyclerAdapter() {
        mList = new AdapterList();
        mList.setListListener(new AdapterListListener(this));
    }

    public void setViewHolderFactory(ViewHolderFactory factory) {
        mHolderFactory = factory;
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

    public void clear() {
        mList.clear();
    }

    @Override
    public final ViewHolder<? extends RecyclerItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHolderFactory == null) {
            throw new IllegalStateException(getClass().getCanonicalName() + " does not have a " + ViewHolderFactory.class.getCanonicalName() + " set. Please call setViewHolder(ViewHolderFactory)");
        }
        return mHolderFactory.createViewHolder(viewType);
    }

    @Override
    public final void onBindViewHolder(ViewHolder holder, int position) {
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
