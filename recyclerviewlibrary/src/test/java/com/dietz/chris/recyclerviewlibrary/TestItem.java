package com.dietz.chris.recyclerviewlibrary;

import android.support.annotation.NonNull;

/**
 *
 */
public class TestItem implements RecyclerItem {

    private final String key;

    public TestItem(String key) {
        super();
        this.key = key;
    }

    @NonNull
    @Override
    public String getIdentityKey() {
        return key;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int compareTo(@NonNull RecyclerItem another) {
        return key.compareTo(another.getIdentityKey());
    }
}
