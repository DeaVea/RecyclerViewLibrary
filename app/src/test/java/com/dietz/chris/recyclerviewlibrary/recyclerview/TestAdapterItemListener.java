package com.dietz.chris.recyclerviewlibrary.recyclerview;

import java.util.ArrayList;

/**
 *
 */
public class TestAdapterItemListener implements AdapterItem.AdapterListener {

    final ArrayList<AdapterItem> mChangedItems = new ArrayList<>();

    public AdapterItem lastChangedItem() {
        return (mChangedItems.isEmpty()) ? null : mChangedItems.get(mChangedItems.size() - 1);
    }

    @Override
    public void itemChanged(AdapterItem item) {
        mChangedItems.add(item);
    }
}
