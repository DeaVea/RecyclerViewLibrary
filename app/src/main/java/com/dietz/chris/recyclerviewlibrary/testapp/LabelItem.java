package com.dietz.chris.recyclerviewlibrary.testapp;

import android.support.annotation.NonNull;

import com.dietz.chris.recyclerviewlibrary.AdapterItem;

/**
 *
 */
public class LabelItem extends AdapterItem {

    static int count = 0;

    private final int type;
    private final String LABEL;

    public LabelItem(int type) {
        LABEL = "LabelItem " + ++count;
        this.type = type;
    }

    @NonNull
    @Override
    public String getIdentityKey() {
        return LABEL;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public int compareTo(@NonNull AdapterItem another) {
        return getIdentityKey().compareTo(another.getIdentityKey());
    }
}
