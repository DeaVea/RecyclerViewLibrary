package com.dietz.chris.recyclerviewlibrary;

import android.support.annotation.NonNull;

/**
 *
 */
public class TestGroupItem<K> extends AdapterItemGroup<K> {

    private final String key;

    public TestGroupItem(String key) {
        super();
        this.key = key;
    }

    public TestGroupItem(K payload) {
        super(payload);
        key = null;
    }

    @NonNull
    @Override
    public String getIdentityKey() {
        return key == null ? super.getIdentityKey() : key;
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public int compareTo(@NonNull AdapterItem another) {
        return getIdentityKey().compareToIgnoreCase(another.getIdentityKey());
    }
}
