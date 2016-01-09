package com.dietz.chris.recyclerviewlibrary.mocks;

import android.support.annotation.NonNull;

import com.dietz.chris.recyclerviewlibrary.RecyclerItem;

/**
 *
 */
public class TestItem implements RecyclerItem {

    private final String key;
    private int type = 0;

    public TestItem(String key) {
        super();
        this.key = key;
    }

    public TestItem setType(int type) {
        this.type = type;
        return this;
    }

    @NonNull
    @Override
    public String getIdentityKey() {
        return key;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public int compareTo(@NonNull RecyclerItem another) {
        return key.compareTo(another.getIdentityKey());
    }
}
