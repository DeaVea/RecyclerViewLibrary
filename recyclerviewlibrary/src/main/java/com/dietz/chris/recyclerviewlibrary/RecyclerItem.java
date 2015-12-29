package com.dietz.chris.recyclerviewlibrary;

/**
 *
 */
public interface RecyclerItem extends Comparable<RecyclerItem> {

    int NOT_TYPE = Integer.MIN_VALUE;

    String getIdentityKey();

    int getType();
}
