package com.dietz.chris.recyclerviewlibrary;

/**
 *
 */
public interface ListListener {

    void onDatasetChanged();

    void onItemChanged(int atPosition, AdapterItem payload);

    void onItemInserted(int atPosition, AdapterItem payload);

    void onItemMoved(int fromPosition, int toPosition, AdapterItem payload);

    void onItemRangedChanged(int positionStart, int itemCount);

    void onItemRangeInserted(int positionStart, int itemCount);

    void onItemRangeRemoved(int positionStart, int itemCount);

    void onItemRemoved(int position, AdapterItem payload);
}
