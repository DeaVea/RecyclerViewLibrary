package com.dietz.chris.recyclerviewlibrary;

import java.util.ArrayList;

/**
 *
 */
public class TestAdapterListListener implements ListListener {

    int dataSetChangeHit = 0;
    ArrayList<InsertObj> itemsPosInserted = new ArrayList<>();
    ArrayList<ChangeObj> itemsPosChanged = new ArrayList<>();
    ArrayList<MoveObj> itemsMoved = new ArrayList<>();
    ArrayList<RangeRemovedObj> itemsRangeRemoved = new ArrayList<>();

    public int lastItemPositionInserted() {
        return (itemsPosInserted.isEmpty()) ? -1 : itemsPosInserted.get(itemsPosInserted.size() - 1).position;
    }

    public int lastItemPositionChanged() {
        return (itemsPosChanged.isEmpty()) ? -1 : itemsPosChanged.get(itemsPosChanged.size() - 1).position;
    }

    public MoveObj lastItemMoved() {
        return (itemsPosChanged.isEmpty()) ? null : itemsMoved.get(itemsMoved.size() - 1);
    }

    public RangeRemovedObj lastItemRangeRemoved() {
        return (itemsRangeRemoved.isEmpty()) ? null : itemsRangeRemoved.get(itemsRangeRemoved.size() - 1);
    }

    @Override
    public void onDatasetChanged() {
        dataSetChangeHit++;
    }

    @Override
    public void onItemChanged(int atPosition, AdapterItem payload) {
        itemsPosChanged.add(new ChangeObj(payload, atPosition));
    }

    @Override
    public void onItemInserted(int atPosition, AdapterItem payload) {
        itemsPosInserted.add(new InsertObj(payload, atPosition));
    }

    @Override
    public void onItemMoved(int fromPosition, int toPosition, AdapterItem payload) {
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
        itemsRangeRemoved.add(new RangeRemovedObj(positionStart, itemCount));
    }

    @Override
    public void onItemRemoved(int position, AdapterItem payload) {

    }

    public static class ChangeObj {
        AdapterItem item;
        int position;

        public ChangeObj(AdapterItem item, int position) {
            this.item = item;
            this.position = position;
        }
    }

    public static class InsertObj {
        AdapterItem item;
        int position;

        public InsertObj(AdapterItem item, int position) {
            this.item = item;
            this.position = position;
        }
    }

    public static class MoveObj {
        AdapterItem item;
        int fromPosition;
        int toPosition;

        public MoveObj(AdapterItem item, int fromPosition, int toPosition) {
            this.item = item;
            this.fromPosition = fromPosition;
            this.toPosition = toPosition;
        }
    }

    public static class RangeRemovedObj {
        int startPosition;
        int count;

        public RangeRemovedObj(int start, int count) {
            this.startPosition = start;
            this.count = count;
        }
    }
}
