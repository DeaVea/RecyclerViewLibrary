package com.dietz.chris.recyclerviewlibrary;

/**
 * ViewHolderFactory creates view holders for the {@link com.dietz.chris.recyclerviewlibrary.RecyclerAdapter}
 */
public interface ViewHolderFactory {

    ViewHolder<? extends RecyclerItem> createViewHolder(int type);
}
