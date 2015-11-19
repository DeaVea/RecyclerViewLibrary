package com.dietz.chris.recyclerviewlibrary.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 *
 */
public class TestAdapterListListener implements ListListener<TestItem> {

    int dataSetChangeHit = 0;
    ArrayList<InsertObj> itemsPosInserted = new ArrayList<>();
    ArrayList<ChangeObj> itemsPosChanged = new ArrayList<>();
    ArrayList<MoveObj> itemsMoved = new ArrayList<>();

    public int lastItemPositionInserted() {
        return (itemsPosInserted.isEmpty()) ? -1 : itemsPosInserted.get(itemsPosInserted.size() - 1).position;
    }

    public int lastItemPositionChanged() {
        return (itemsPosChanged.isEmpty()) ? -1 : itemsPosChanged.get(itemsPosChanged.size() - 1).position;
    }

    public MoveObj lastItemMoved() {
        return (itemsPosChanged.isEmpty()) ? null : itemsMoved.get(itemsMoved.size() - 1);
    }

    @Override
    public void onDatasetChanged() {
        dataSetChangeHit++;
    }

    @Override
    public void onItemChanged(int atPosition, TestItem payload) {
        itemsPosChanged.add(new ChangeObj(payload, atPosition));
    }

    @Override
    public void onItemInserted(int atPosition, TestItem payload) {
        itemsPosInserted.add(new InsertObj(payload, atPosition));
    }

    @Override
    public void onItemMoved(int fromPosition, int toPosition, TestItem payload) {
        itemsMoved.add(new MoveObj(payload, fromPosition, toPosition));
    }

    @Override
    public void onItemRangedChanged(int positionStart, int itemCount) {

    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {

    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {

    }

    @Override
    public void onItemRemoved(int position, TestItem payload) {

    }

    public static class ChangeObj {
        TestItem item;
        int position;

        public ChangeObj(TestItem item, int position) {
            this.item = item;
            this.position = position;
        }
    }

    public static class InsertObj {
        TestItem item;
        int position;

        public InsertObj(TestItem item, int position) {
            this.item = item;
            this.position = position;
        }
    }

    public static class MoveObj {
        TestItem item;
        int fromPosition;
        int toPosition;

        public MoveObj(TestItem item, int fromPosition, int toPosition) {
            this.item = item;
            this.fromPosition = fromPosition;
            this.toPosition = toPosition;
        }
    }
}
