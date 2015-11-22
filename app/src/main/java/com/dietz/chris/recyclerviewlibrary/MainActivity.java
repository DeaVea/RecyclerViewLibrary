package com.dietz.chris.recyclerviewlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dietz.chris.recyclerviewlibrary.recyclerview.AdapterItem;
import com.dietz.chris.recyclerviewlibrary.recyclerview.RecyclerAdapter;

public class MainActivity extends AppCompatActivity implements MainAdapter.MainAdapterListener, View.OnClickListener {

    private static final int TYPE_LABEL = MainAdapter.TYPE_ITEM;
    private static final int TYPE_GROUP = MainAdapter.TYPE_GROUP;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter = new MainAdapter());
        adapter.setMainAdapterListener(this);

        findViewById(R.id.idBtnAddItem).setOnClickListener(this);
        findViewById(R.id.idBtnAddGroup).setOnClickListener(this);
    }

    @Override
    public void onItemClicked(LabelItem item) {
        adapter.removeItem(item);
    }

    @Override
    public void onAddItemToGroup(GroupItem group) {
        group.addOrUpdateItem(new LabelItem(TYPE_LABEL));
    }

    @Override
    public void onAddGroupToGroup(GroupItem group) {
        group.addOrUpdateItem(new GroupItem(TYPE_GROUP));
    }

    @Override
    public void onDeleteGroup(GroupItem group) {
        adapter.removeItem(group);
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
}
