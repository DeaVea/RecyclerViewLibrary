package com.dietz.chris.recyclerviewlibrary.recyclerview;

import java.util.ArrayList;

/**
 *
 */
public class AdapterList<K extends AdapterItem> {

    private final ArrayList<K> mMainList;

    private ListListener<K> mListListener;

    public AdapterList() {
        mMainList = new ArrayList<>();
    }

    public void setListListener(ListListener<K> listListener) {
        mListListener = listListener;
    }

    public void clear() {

    }
}
