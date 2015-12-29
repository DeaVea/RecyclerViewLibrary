package com.dietz.chris.recyclerviewlibrary.testapp;

import android.support.annotation.NonNull;

import com.dietz.chris.recyclerviewlibrary.RecyclerItem;

/**
 *
 */
public class LabelItem implements RecyclerItem {

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
    public int compareTo(@NonNull RecyclerItem another) {
        return getIdentityKey().compareTo(another.getIdentityKey());
    }
}
