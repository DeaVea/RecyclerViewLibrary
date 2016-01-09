package com.dietz.chris.recyclerviewlibrary.mocks;

import android.support.annotation.NonNull;

import com.dietz.chris.recyclerviewlibrary.RecyclerGroupItem;
import com.dietz.chris.recyclerviewlibrary.RecyclerItem;

/**
 *
 */
public class TestGroupItem implements RecyclerGroupItem {

    private final String key;
    private int type = 0;

    public TestGroupItem(String key) {
        this.key = key;
    }

    public TestGroupItem setType(int type) {
        this.type = type;
        return this;
    }

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
        return getIdentityKey().compareTo(another.getIdentityKey());
    }
}
