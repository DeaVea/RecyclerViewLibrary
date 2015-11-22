package com.dietz.chris.recyclerviewlibrary;

import android.support.annotation.NonNull;

import com.dietz.chris.recyclerviewlibrary.recyclerview.AdapterItem;

/**
 *
 */
public class LabelItem extends AdapterItem {

    static int count = 0;

    private final String LABEL;

    public LabelItem() {
        LABEL = "LabelItem " + ++count;
    }

    @NonNull
    @Override
    public String getIdentityKey() {
        return LABEL;
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public int compareTo(@NonNull AdapterItem another) {
        return getIdentityKey().compareTo(another.getIdentityKey());
    }
}
