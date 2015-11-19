package com.dietz.chris.recyclerviewlibrary.recyclerview;

/**
 *
 */
public interface ListListener<K> {

    void onDatasetChanged();

    void onItemChanged(int atPosition, K payload);

    void onItemInserted(int atPosition, K payload);

    void onItemMoved(int fromPosition, int toPosition, K payload);

    void onItemRangedChanged(int positionStart, int itemCount);

    void onItemRangeInserted(int positionStart, int itemCount);

    void onItemRangeRemoved(int positionStart, int itemCount);

    void onItemRemoved(int position, K payload);
}
