package com.dietz.chris.recyclerviewlibrary.testapp;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dietz.chris.recyclerviewlibrary.ViewHolder;

/**
 *
 */
public class ItemViewHolder extends ViewHolder<LabelItem> {
    TextView mTv;

    /**
     * @param inflater
     *         LayoutInflater to inflate views
     * @param parent
     *         Parent of the view holder (generally the RecyclerView itself)
     * @param id
     *      Layout ID to inflate
     */
    public ItemViewHolder(LayoutInflater inflater, ViewGroup parent, @LayoutRes int id) {
        super(inflater, parent, id);
        mTv = findView(R.id.txtLabelName);
    }

    @Override
    public void onBind(LabelItem item) {
        mTv.setText(item.getIdentityKey());
    }
}
