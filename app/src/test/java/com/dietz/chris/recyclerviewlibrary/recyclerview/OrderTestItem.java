package com.dietz.chris.recyclerviewlibrary.recyclerview;

import android.support.annotation.NonNull;

/**
 *
 */
public class OrderTestItem extends TestItem {

    private int order = 0;

    public OrderTestItem(String key, int order) {
        super(key);
        this.order = order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public int compareTo(@NonNull AdapterItem item) {
        return order - ((OrderTestItem) item).order;
    }
}
