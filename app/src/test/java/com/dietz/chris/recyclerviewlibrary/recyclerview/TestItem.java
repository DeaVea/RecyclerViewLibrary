package com.dietz.chris.recyclerviewlibrary.recyclerview;

import android.support.annotation.NonNull;

/**
 *
 */
public class TestItem extends AdapterItem {

    private final String key;

    public TestItem(String key) {
        this.key = key;
    }

    public int getPositionInList() {
        return super.getPositionInList();
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
    public int compareTo(@NonNull AdapterItem another) {
        return this.key.compareToIgnoreCase(another.getIdentityKey());
    }
}
