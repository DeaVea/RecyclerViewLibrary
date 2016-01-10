package com.dietz.chris.recyclerviewlibrary.testapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.dietz.chris.recyclerviewlibrary.Action;
import com.dietz.chris.recyclerviewlibrary.RecyclerAdapter;
import com.dietz.chris.recyclerviewlibrary.RecyclerItem;
import com.dietz.chris.recyclerviewlibrary.ViewHolder;
import com.dietz.chris.recyclerviewlibrary.ViewHolderFactory;
import com.dietz.chris.recyclerviewlibrary.core.AdapterItem;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int TYPE_LABEL = 1;
    private static final int TYPE_GROUP = 2;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter = new RecyclerAdapter());
        adapter.setViewHolderFactory(new ViewHolderFactory() {
            @Override
            public ViewHolder<? extends RecyclerItem> createViewHolder(int type) {
                switch (type) {
                    case TYPE_LABEL:
                        return createLabelHolder();
                    case TYPE_GROUP:
                        return createGroupHolder();
                }
                return null;
            }
        });

        findViewById(R.id.idBtnAddItem).setOnClickListener(this);
        findViewById(R.id.idBtnAddGroup).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idBtnAddItem:
                adapter.addItem(new LabelItem(TYPE_LABEL));
                break;
            case R.id.idBtnAddGroup:
                adapter.addItem(new GroupItem(TYPE_GROUP));
                break;
        }
    }

    private ViewHolder<LabelItem> createLabelHolder() {
        return new ItemViewHolder(LayoutInflater.from(this), null, R.layout.labelitem)
                .addMainAction(new Action<LabelItem>() {
                    @Override
                    public void action(int actionId, LabelItem boundItem) {
                        adapter.removeItem(boundItem);
                    }
                });
    }

    private ViewHolder<GroupItem> createGroupHolder() {
        return new GroupItemViewHolder(LayoutInflater.from(this), null, R.layout.groupitem)
                .addMainAction(new Action<GroupItem>() {
                    @Override
                    public void action(int actionId, GroupItem boundItem) {
                        adapter.removeItem(boundItem);
                    }
                })
                .addAction(R.id.btnAddGroup, new Action<GroupItem>() {
                    @Override
                    public void action(int actionId, GroupItem boundItem) {
                        AdapterItem adapterItem = adapter.findAdapterItem(boundItem.getIdentityKey());
                        adapterItem.addOrUpdatePayload(new GroupItem(TYPE_GROUP));
                    }
                })
                .addAction(R.id.btnAddItem, new Action<GroupItem>() {
                    @Override
                    public void action(int actionId, GroupItem boundItem) {
                        AdapterItem adapterItem = adapter.findAdapterItem(boundItem.getIdentityKey());
                        adapterItem.addOrUpdatePayload(new LabelItem(TYPE_LABEL));
                    }
                });
    }
}
