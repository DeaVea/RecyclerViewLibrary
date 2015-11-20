package com.dietz.chris.recyclerviewlibrary;

import android.app.LauncherActivity;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dietz.chris.recyclerviewlibrary.recyclerview.RecyclerAdapter;
import com.dietz.chris.recyclerviewlibrary.recyclerview.ViewHolder;

/**
 *
 */
public class MainAdapter extends RecyclerAdapter<LabelItem> {

    public interface MainAdapterListener {
        void onItemClicked(LabelItem item);
    }

    private MainAdapterListener mMainAdapterListener;

    @Override
    public ViewHolder<LabelItem> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new InternalViewHolder(inflater, parent, R.layout.labelitem);
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
}
