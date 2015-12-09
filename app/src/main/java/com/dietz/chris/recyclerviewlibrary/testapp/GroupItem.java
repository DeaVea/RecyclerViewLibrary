package com.dietz.chris.recyclerviewlibrary.testapp;

import android.support.annotation.NonNull;

import com.dietz.chris.recyclerviewlibrary.AdapterItem;
import com.dietz.chris.recyclerviewlibrary.AdapterItemGroup;

/**
 *
 */
public class GroupItem extends AdapterItemGroup {

    static int count = 0;

    private final int type;
    private final String LABEL;

    public GroupItem(int type) {
        LABEL = "Group Item " + ++count;
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
