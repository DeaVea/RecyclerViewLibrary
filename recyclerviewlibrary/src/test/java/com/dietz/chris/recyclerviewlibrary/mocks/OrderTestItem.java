package com.dietz.chris.recyclerviewlibrary.mocks;

import android.support.annotation.NonNull;

import com.dietz.chris.recyclerviewlibrary.RecyclerItem;

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
    public int compareTo(@NonNull RecyclerItem item) {
        return order - ((OrderTestItem) item).order;
    }
}
