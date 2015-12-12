package com.dietz.chris.recyclerviewlibrary;

import android.support.annotation.NonNull;

/**
 *
 */
public class TestItem<K> extends AdapterItem<K> {

    private final String key;

    public TestItem(String key) {
        super();
        this.key = key;
    }

    public TestItem(K payload) {
        super(payload);
        key = null;
    }

    @NonNull
    @Override
    public String getIdentityKey() {
        return (key != null) ? key : super.getIdentityKey();
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int compareTo(@NonNull AdapterItem another) {
        return getIdentityKey().compareToIgnoreCase(another.getIdentityKey());
    }
}
