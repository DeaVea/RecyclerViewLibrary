package com.dietz.chris.recyclerviewlibrary.testapp;

import android.support.annotation.NonNull;

import com.dietz.chris.recyclerviewlibrary.RecyclerGroupItem;
import com.dietz.chris.recyclerviewlibrary.RecyclerItem;

/**
 *
 */
public class GroupItem implements RecyclerGroupItem {

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
    public int compareTo(@NonNull RecyclerItem another) {
        return getIdentityKey().compareTo(another.getIdentityKey());
    }
}
