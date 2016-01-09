package com.dietz.chris.recyclerviewlibrary;

/**
 *
 */
public interface Action<K extends RecyclerItem> {

    void action(int actionId, K boundItem);
}
