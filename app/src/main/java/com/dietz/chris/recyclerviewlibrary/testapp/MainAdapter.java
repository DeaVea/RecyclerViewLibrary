package com.dietz.chris.recyclerviewlibrary.testapp;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dietz.chris.recyclerviewlibrary.AdapterItem;
import com.dietz.chris.recyclerviewlibrary.RecyclerAdapter;
import com.dietz.chris.recyclerviewlibrary.ViewHolder;

/**
 *
 */
public class MainAdapter extends RecyclerAdapter {

    public static int TYPE_ITEM = 1;
    public static int TYPE_GROUP = 2;

    public interface MainAdapterListener {
        void onItemClicked(GroupItem item);
        void onItemClicked(LabelItem item);
        void onAddItemToGroup(GroupItem group);
        void onAddGroupToGroup(GroupItem group);
        void onDeleteGroup(GroupItem group);
    }

    private MainAdapterListener mMainAdapterListener;

    @Override
    public ViewHolder<? extends AdapterItem> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new InternalViewHolder(inflater, parent, R.layout.labelitem);
        } else if (viewType == TYPE_GROUP) {
            return new InternalGroupViewHolder(inflater, parent, R.layout.groupitem);
        }
        throw new IllegalArgumentException("viewType " + viewType + " is not supported in this adapter.");
    }

    public void setMainAdapterListener(MainAdapterListener listener) {
        mMainAdapterListener = listener;
    }

    private class InternalViewHolder extends ViewHolder<LabelItem> implements View.OnClickListener {

        TextView name;

        public InternalViewHolder(LayoutInflater inflater, ViewGroup parent, @LayoutRes int id) {
            super(inflater, parent, id);
            name = findView(R.id.txtLabelName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onBind(LabelItem item) {
            name.setText(item.getIdentityKey());
        }

        @Override
        public void onClick(View v) {
            mMainAdapterListener.onItemClicked(getBoundItem());
        }
    }

    private class InternalGroupViewHolder extends ViewHolder<GroupItem> implements View.OnClickListener {

        TextView name;

        public InternalGroupViewHolder(LayoutInflater inflater, ViewGroup parent, @LayoutRes int id) {
            super(inflater, parent, id);
            name = findView(R.id.txtLabelName);
            itemView.setOnClickListener(this);
            findView(R.id.btnAddItem).setOnClickListener(this);
            findView(R.id.btnAddGroup).setOnClickListener(this);
            findView(R.id.btnDeleteGroup).setOnClickListener(this);
        }

        @Override
        public void onBind(GroupItem item) {
            name.setText(item.getIdentityKey());
        }

        @Override
        public void onClick(View v) {
            if (v == itemView) {
                mMainAdapterListener.onItemClicked(getBoundItem());
                return;
            }
            switch (v.getId()) {
                case R.id.btnAddItem:
                    mMainAdapterListener.onAddItemToGroup(getBoundItem());
                    break;
                case R.id.btnAddGroup:
                    mMainAdapterListener.onAddGroupToGroup(getBoundItem());
                    break;
                case R.id.btnDeleteGroup:
                    mMainAdapterListener.onDeleteGroup(getBoundItem());
                    break;
            }
        }
    }
}
