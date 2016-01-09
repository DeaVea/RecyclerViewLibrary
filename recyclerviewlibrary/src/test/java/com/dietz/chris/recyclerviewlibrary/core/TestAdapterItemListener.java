package com.dietz.chris.recyclerviewlibrary.core;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 *
 */
public class TestAdapterItemListener implements AdapterListener {

    final ArrayList<AdapterItem> mChangedItems = new ArrayList<>();

    public AdapterItem lastChangedItem() {
        return (mChangedItems.isEmpty()) ? null : mChangedItems.get(mChangedItems.size() - 1);
    }

    @Override
    public void itemChanged(@NonNull AdapterItem item) {
        mChangedItems.add(item);
    }

    @Override
    public void itemAdded(@NonNull AdapterItemGroup container, @NonNull AdapterItem item, int atPosition) {

    }

    @Override
    public void itemsAdded(@NonNull AdapterItemGroup container, @NonNull int fromPosition, int size) {

    }

    @Override
    public void itemRemoved(@NonNull AdapterItemGroup container, @NonNull AdapterItem item, int fromPosition) {

    }

    @Override
    public void itemsRemoved(@NonNull AdapterItemGroup container, @NonNull int fromPosition, @NonNull int size) {

    }
}
