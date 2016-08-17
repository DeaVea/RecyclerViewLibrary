/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dietz.chris.recyclerviewlibrary.core;

import java.util.ArrayList;

/**
 *
 */
public class TestAdapterListListener implements ListListener {

    int dataSetChangeHit = 0;
    ArrayList<InsertObj> itemsPosInserted = new ArrayList<>();
    ArrayList<RangeInsertedObj> itemsRangeInserted = new ArrayList<>();
    ArrayList<ChangeObj> itemsPosChanged = new ArrayList<>();
    ArrayList<MoveObj> itemsMoved = new ArrayList<>();
    ArrayList<RemoveObj> itemsRemoved = new ArrayList<>();
    ArrayList<RangeRemovedObj> itemsRangeRemoved = new ArrayList<>();

    public int lastItemPositionInserted() {
        return (itemsPosInserted.isEmpty()) ? -1 : itemsPosInserted.get(itemsPosInserted.size() - 1).position;
    }

    public int lastItemPositionChanged() {
        return (itemsPosChanged.isEmpty()) ? -1 : itemsPosChanged.get(itemsPosChanged.size() - 1).position;
    }

    public MoveObj lastItemMoved() {
        return (itemsMoved.isEmpty()) ? null : itemsMoved.get(itemsMoved.size() - 1);
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
        itemsRangeInserted.add(new RangeInsertedObj(positionStart, itemCount));
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        itemsRangeRemoved.add(new RangeRemovedObj(positionStart, itemCount));
    }

    @Override
    public void onItemRemoved(int position, AdapterItem payload) {
        itemsRemoved.add(new RemoveObj(payload, position));
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

    public static class RemoveObj {
        AdapterItem item;
        int pos;

        public RemoveObj(AdapterItem item, int pos) {
            this.item = item;
            this.pos = pos;
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

    public static class RangeInsertedObj {
        int startPosition;
        int count;

        public RangeInsertedObj(int start, int count) {
            this.startPosition = start;
            this.count = count;
        }
    }
}
