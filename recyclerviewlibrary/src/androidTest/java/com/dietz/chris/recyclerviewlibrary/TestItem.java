package com.dietz.chris.recyclerviewlibrary;

import android.support.annotation.NonNull;

/**
 *
 */
public class TestItem implements RecyclerItem {
    private static int count = 0;

    private String key;
    private int type;

    public TestItem() {
        count++;
        key = String.valueOf(count);
        type = 0;
    }

    public TestItem(String key) {
        count++;
        this.key = key;
        type = 0;
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
    public int compareTo(@NonNull RecyclerItem recyclerItem) {
        return 0;
    }
}
