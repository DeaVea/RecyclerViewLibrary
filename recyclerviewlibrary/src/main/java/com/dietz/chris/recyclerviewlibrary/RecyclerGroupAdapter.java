package com.dietz.chris.recyclerviewlibrary;

import android.support.annotation.Nullable;

import com.dietz.chris.recyclerviewlibrary.core.AdapterListListener;

/**
 * A {@link RecyclerAdapter} that supports grouping.
 */
public class RecyclerGroupAdapter<G extends RecyclerGroupItem, K extends RecyclerItem> extends RecyclerAdapter<K> {

    public RecyclerGroupAdapter() {
        super();
    }

    RecyclerGroupAdapter(AdapterListListener listListener) {
        super(listListener);
    }

    public void addItem(@Nullable G item) {
        if (item != null) {
            getList().addItem(item);
        }
    }

    public void removeItem(@Nullable G item) {
        if (item != null) {
            getList().removeItem(item);
        }
    }

    public void addItemToGroup(@Nullable G group, @Nullable K item) {
        if (group != null && item != null) {
            getList().addItemToGroup(item, group);
        }
    }

    public void addItemToGroup(@Nullable G group, @Nullable G otherGroup) {
        if (group != null && otherGroup != null) {
            getList().addItemToGroup(otherGroup, group);
        }
    }
}
