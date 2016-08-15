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

import com.dietz.chris.recyclerviewlibrary.RecyclerItem;
import com.dietz.chris.recyclerviewlibrary.mocks.OrderTestItem;
import com.dietz.chris.recyclerviewlibrary.mocks.TestItem;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
public class DuelCollectionTests {

    @Test
    public void filterItems() {
        TestAdapterListListener listListener = new TestAdapterListListener();
        final AdapterItemCollection items = new AdapterItemCollection(listListener);

        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("A", 0)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("B", 1)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("C", 2)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("D", 3)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("E", 4)));
        items.addOrUpdate(new AdapterItem<>(new TestItem("F")));
        items.addOrUpdate(new AdapterItem<>(new TestItem("G")));
        items.addOrUpdate(new AdapterItem<>(new TestItem("H")));

        items.applyFilter(new Filter<OrderTestItem>() {
            @Override
            public boolean accept(OrderTestItem value) {
                return value.getIdentityKey().equals("B");
            }
        }, OrderTestItem.class);

        // One ordered item and three regular
        assertThat(items.size(), is(4));
        assertThat(items.get(0).getIdentityKey(), is("B")); // "A", "C", "D", "E" should be gone.
        assertThat(items.get(1).getIdentityKey(), is("F"));
        assertThat(items.get(2).getIdentityKey(), is("G"));
        assertThat(items.get(3).getIdentityKey(), is("H"));

        assertThat(listListener.itemsRangeRemoved.size(), is(4));

        items.applyFilter(new Filter<TestItem>() {
            @Override
            public boolean accept(TestItem value) {
                return value.getIdentityKey().equals("G");
            }
        }, TestItem.class);

        assertThat(items.size(), is(2));
        assertThat(items.get(0).getIdentityKey(), is("B"));
        assertThat(items.get(1).getIdentityKey(), is("G"));

        assertThat(listListener.itemsRangeRemoved.size(), is(6));

        items.applyFilter(null, OrderTestItem.class);

        assertThat(items.size(), is(6));
        assertThat(items.get(0).getIdentityKey(), is("A"));
        assertThat(items.get(1).getIdentityKey(), is("B"));
        assertThat(items.get(2).getIdentityKey(), is("C"));
        assertThat(items.get(3).getIdentityKey(), is("D"));
        assertThat(items.get(4).getIdentityKey(), is("E"));
        assertThat(items.get(5).getIdentityKey(), is("G"));

        assertThat(listListener.itemsRangeInserted.size(), is(4));

        items.applyFilter(null, TestItem.class);

        assertThat(items.size(), is(8));
        assertThat(items.get(0).getIdentityKey(), is("A"));
        assertThat(items.get(1).getIdentityKey(), is("B"));
        assertThat(items.get(2).getIdentityKey(), is("C"));
        assertThat(items.get(3).getIdentityKey(), is("D"));
        assertThat(items.get(4).getIdentityKey(), is("E"));
        assertThat(items.get(5).getIdentityKey(), is("F"));
        assertThat(items.get(6).getIdentityKey(), is("G"));
        assertThat(items.get(7).getIdentityKey(), is("H"));

        assertThat(listListener.itemsRangeInserted.size(), is(6));
    }

    @Test
    public void filterDeep() {
        Collection<AdapterItem> deepItems = new ArrayList<>(3);
        deepItems.add(new AdapterItem<>(new TestItem("F")));
        deepItems.add(new AdapterItem<>(new TestItem("G")));
        deepItems.add(new AdapterItem<>(new TestItem("H")));

        TestAdapterListListener listListener = new TestAdapterListListener();
        final AdapterItemCollection items = new AdapterItemCollection(listListener);

        AdapterItemGroup group = new AdapterItemGroup<>(new OrderTestItem("A", 0));
        group.addOrUpdateItems(deepItems);
        items.addOrUpdate(group);

        deepItems = new ArrayList<>(3);
        deepItems.add(new AdapterItem<>(new TestItem("F")));
        deepItems.add(new AdapterItem<>(new TestItem("G")));
        deepItems.add(new AdapterItem<>(new TestItem("H")));

        group = new AdapterItemGroup<>(new OrderTestItem("B", 1));
        group.addOrUpdateItems(deepItems);
        items.addOrUpdate(group);

        deepItems = new ArrayList<>(3);
        deepItems.add(new AdapterItem<>(new TestItem("F")));
        deepItems.add(new AdapterItem<>(new TestItem("G")));
        deepItems.add(new AdapterItem<>(new TestItem("H")));

        group = new AdapterItemGroup<>(new OrderTestItem("C", 2));
        group.addOrUpdateItems(deepItems);
        items.addOrUpdate(group);

        deepItems = new ArrayList<>(3);
        deepItems.add(new AdapterItem<>(new TestItem("F")));
        deepItems.add(new AdapterItem<>(new TestItem("G")));
        deepItems.add(new AdapterItem<>(new TestItem("H")));

        group = new AdapterItemGroup<>(new OrderTestItem("D", 3));
        group.addOrUpdateItems(deepItems);
        items.addOrUpdate(group);

        deepItems = new ArrayList<>(3);
        deepItems.add(new AdapterItem<>(new TestItem("F")));
        deepItems.add(new AdapterItem<>(new TestItem("G")));
        deepItems.add(new AdapterItem<>(new TestItem("H")));

        group = new AdapterItemGroup<>(new OrderTestItem("E", 4));
        group.addOrUpdateItems(deepItems);
        items.addOrUpdate(group);

        assertThat(items.size(), is(20));

        items.applyFilter(new Filter<TestItem>() {
            @Override
            public boolean accept(TestItem value) {
                return value.getIdentityKey().equals("G");
            }
        }, TestItem.class);

        assertThat(items.size(), is(10));
        assertThat(items.get(0).getIdentityKey(), is("A"));
        assertThat(items.get(1).getIdentityKey(), is("G"));

        items.applyFilter(null, TestItem.class);

        assertThat(items.size(), is(20));
        assertThat(items.get(0).getIdentityKey(), is("A"));
        assertThat(items.get(1).getIdentityKey(), is("F"));
        assertThat(items.get(2).getIdentityKey(), is("G"));
        assertThat(items.get(3).getIdentityKey(), is("H"));
        assertThat(items.get(4).getIdentityKey(), is("B"));

        items.applyFilter(new Filter<OrderTestItem>() {
            @Override
            public boolean accept(OrderTestItem value) {
                return value.getIdentityKey().equals("B");
            }
        }, OrderTestItem.class);

        assertThat(items.size(), is(4));
        assertThat(items.get(0).getIdentityKey(), is("B"));
        assertThat(items.get(1).getIdentityKey(), is("F"));
        assertThat(items.get(2).getIdentityKey(), is("G"));
        assertThat(items.get(3).getIdentityKey(), is("H"));

        items.applyFilter(null, OrderTestItem.class);

        assertThat(items.size(), is(20));
        assertThat(items.get(0).getIdentityKey(), is("A"));
        assertThat(items.get(1).getIdentityKey(), is("F"));
        assertThat(items.get(2).getIdentityKey(), is("G"));
        assertThat(items.get(3).getIdentityKey(), is("H"));
        assertThat(items.get(4).getIdentityKey(), is("B"));
    }

    @Test
    public void addAndSizeTests() {
        TestAdapterListListener listListener = new TestAdapterListListener();
        final AdapterItemCollection items = new AdapterItemCollection(listListener);

        assertThat(items.size(), equalTo(0));

        int position = items.addOrUpdate(new AdapterItem<>(new TestItem("C")));
        assertThat(position, equalTo(0));
        assertThat(items.size(), equalTo(1));
        assertThat(listListener.itemsPosInserted.size(), equalTo(1));
        assertThat(listListener.lastItemPositionInserted(), equalTo(0));

        position = items.addOrUpdate(new AdapterItem<>(new TestItem("A")));
        assertThat(position, equalTo(0));
        assertThat(items.size(), equalTo(2));
        assertThat(listListener.itemsPosInserted.size(), equalTo(2));
        assertThat(listListener.lastItemPositionInserted(), equalTo(0));

        position = items.addOrUpdate(new AdapterItem<>(new TestItem("B")));
        assertThat(position, equalTo(1));
        assertThat(items.size(), equalTo(3));
        assertThat(listListener.itemsPosInserted.size(), equalTo(3));
        assertThat(listListener.lastItemPositionInserted(), equalTo(1));

        position = items.addOrUpdate(new AdapterItem<>(new TestItem("D")));
        assertThat(position, equalTo(3));
        assertThat(items.size(), equalTo(4));
        assertThat(listListener.itemsPosInserted.size(), equalTo(4));
        assertThat(listListener.lastItemPositionInserted(), equalTo(3));
    }

    @Test
    public void updateTests() {
        TestAdapterListListener listListener = new TestAdapterListListener();
        final AdapterItemCollection items = new AdapterItemCollection(listListener);

        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("A", 0)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("B", 1)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("C", 2)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("D", 3)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("E", 4)));

        assertThat(items.size(), equalTo(5));

        int position = items.addOrUpdate(new AdapterItem<>(new OrderTestItem("A", 0)));
        assertThat(position, equalTo(0));
        assertThat(listListener.itemsPosChanged.size(), equalTo(1));
        assertThat(listListener.lastItemPositionChanged(), equalTo(0));

        position = items.addOrUpdate(new AdapterItem<>(new OrderTestItem("B", 1)));
        assertThat(position, equalTo(1));
        assertThat(listListener.itemsPosChanged.size(), equalTo(2));
        assertThat(listListener.lastItemPositionChanged(), equalTo(1));

        position = items.addOrUpdate(new AdapterItem<>(new OrderTestItem("C", 2)));
        assertThat(position, equalTo(2));
        assertThat(listListener.itemsPosChanged.size(), equalTo(3));
        assertThat(listListener.lastItemPositionChanged(), equalTo(2));

        position = items.addOrUpdate(new AdapterItem<>(new OrderTestItem("D", 3)));
        assertThat(position, equalTo(3));
        assertThat(listListener.itemsPosChanged.size(), equalTo(4));
        assertThat(listListener.lastItemPositionChanged(), equalTo(3));

        position = items.addOrUpdate(new AdapterItem<>(new OrderTestItem("E", 4)));
        assertThat(position, equalTo(4));
        assertThat(listListener.itemsPosChanged.size(), equalTo(5));
        assertThat(listListener.lastItemPositionChanged(), equalTo(4));

        assertThat(items.size(), equalTo(5));

        position = items.addOrUpdate(new AdapterItem<>(new OrderTestItem("A", 1)));
        assertThat(position, equalTo(0));
        assertThat(listListener.itemsMoved.size(), equalTo(0));
        assertThat(listListener.lastItemPositionChanged(), equalTo(0));

        position = items.addOrUpdate(new AdapterItem<>(new OrderTestItem("B", 0)));
        assertThat(position, equalTo(0));
        assertThat(listListener.itemsMoved.size(), equalTo(1));
        assertThat(listListener.lastItemMoved().fromPosition, equalTo(1));
        assertThat(listListener.lastItemMoved().toPosition, equalTo(0));

        position = items.addOrUpdate(new AdapterItem<>(new OrderTestItem("C", 10)));
        assertThat(position, equalTo(4));
        assertThat(listListener.itemsMoved.size(), equalTo(2));
        assertThat(listListener.lastItemMoved().fromPosition, equalTo(2));
        assertThat(listListener.lastItemMoved().toPosition, equalTo(4));

        position = items.addOrUpdate(new AdapterItem<>(new OrderTestItem("A", 7)));
        assertThat(position, equalTo(3));
        assertThat(listListener.itemsMoved.size(), equalTo(3));
        assertThat(listListener.lastItemMoved().fromPosition, equalTo(1));
        assertThat(listListener.lastItemMoved().toPosition, equalTo(3));
    }

    @Test
    public void getTests() {
        TestAdapterListListener listListener = new TestAdapterListListener();
        final AdapterItemCollection items = new AdapterItemCollection(listListener);

        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("A", 0)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("B", 1)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("C", 2)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("D", 3)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("E", 4)));

        assertThat(items.get(0).getIdentityKey(), equalTo("A"));
        assertThat(items.get(1).getIdentityKey(), equalTo("B"));
        assertThat(items.get(2).getIdentityKey(), equalTo("C"));
        assertThat(items.get(3).getIdentityKey(), equalTo("D"));
        assertThat(items.get(4).getIdentityKey(), equalTo("E"));
    }

    @Test
    public void clearTests() {
        TestAdapterListListener listListener = new TestAdapterListListener();
        final AdapterItemCollection items = new AdapterItemCollection(listListener);

        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("A", 0)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("B", 1)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("C", 2)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("D", 3)));
        items.addOrUpdate(new AdapterItem<>(new OrderTestItem("E", 4)));

        assertThat(items.size(), equalTo(5));

        items.clear();

        assertThat(items.size(), equalTo(0));

        assertThat(listListener.itemsRangeRemoved.size(), equalTo(1));
        assertThat(listListener.lastItemRangeRemoved().startPosition, equalTo(0));
        assertThat(listListener.lastItemRangeRemoved().count, equalTo(5));
    }

    @Test
    public void containsPayloadTests() {
        final AdapterItemCollection items = new AdapterItemCollection(null);

        TestItem payload1 = new TestItem("A");
        TestItem payload2 = new TestItem("B");
        TestItem payload3 = new TestItem("C");
        TestItem payload4 = new TestItem("D");
        TestItem payload5 = new TestItem("A");

        items.addOrUpdate(new AdapterItem<>(payload1));
        items.addOrUpdate(new AdapterItem<>(payload2));
        items.addOrUpdate(new AdapterItem<>(payload3));
        items.addOrUpdate(new AdapterItem<>(payload4));
        items.addOrUpdate(new AdapterItem<>(payload5));

        assertThat(items.containsPayload(payload1), equalTo(true));
        assertThat(items.containsPayload(payload2), equalTo(true));
        assertThat(items.containsPayload(payload3), equalTo(true));
        assertThat(items.containsPayload(payload4), equalTo(true));
        assertThat(items.containsPayload(payload5), equalTo(true));
    }

    @Test
    public void removePayloadTests() {
        final AdapterItemCollection items = new AdapterItemCollection(null);

        TestItem payload1 = new TestItem("A");
        TestItem payload2 = new TestItem("B");
        TestItem payload3 = new TestItem("C");
        TestItem payload4 = new TestItem("D");
        TestItem payload5 = new TestItem("A");

        items.addOrUpdate(new AdapterItem<>(payload1));
        items.addOrUpdate(new AdapterItem<>(payload2));
        items.addOrUpdate(new AdapterItem<>(payload3));
        items.addOrUpdate(new AdapterItem<>(payload4));
        items.addOrUpdate(new AdapterItem<>(payload5));

        assertThat(items.removeItemWithPayload(payload1), equalTo(1));
        assertThat(items.removeItemWithPayload(payload3), equalTo(1));
        assertThat(items.removeItemWithPayload(payload1), equalTo(0));
        assertThat(items.removeItemWithPayload(payload5), equalTo(0));
        assertThat(items.removeItemWithPayload(payload4), equalTo(1));
        assertThat(items.removeItemWithPayload(payload2), equalTo(1));

        items.clear();

        TestItem groupPayload1 = new TestItem("Group1");
        AdapterItemGroup group1 = new AdapterItemGroup<>(groupPayload1);
        group1.addOrUpdateItem(new AdapterItem<>(payload1));
        group1.addOrUpdateItem(new AdapterItem<>(payload2));

        TestItem groupPayload2 = new TestItem("Group2");
        AdapterItemGroup group2 = new AdapterItemGroup<>(groupPayload2);
        group2.addOrUpdateItem(new AdapterItem<>(payload3));
        group2.addOrUpdateItem(new AdapterItem<>(payload4));

        TestItem groupPayload3 = new TestItem("Group3");
        AdapterItemGroup group3 = new AdapterItemGroup<>(groupPayload3);
        group3.addOrUpdateItem(new AdapterItem<>(payload1));
        group3.addOrUpdateItem(new AdapterItem<>(payload2));
        group3.addOrUpdateItem(new AdapterItem<>(payload3));
        group3.addOrUpdateItem(new AdapterItem<>(payload4));

        items.addOrUpdate(group1);
        items.addOrUpdate(group2);
        items.addOrUpdate(group3);

        assertThat(items.removeItemWithPayload(payload1), equalTo(2));
        assertThat(group1.getItemCount(), equalTo(2));
        assertThat(group3.getItemCount(), equalTo(4));

        assertThat(items.removeItemWithPayload(payload1), equalTo(0));

        assertThat(items.removeItemWithPayload(payload3), equalTo(2));
        assertThat(group1.getItemCount(), equalTo(2));
        assertThat(group2.getItemCount(), equalTo(2));
        assertThat(group3.getItemCount(), equalTo(3));

        assertThat(items.removeItemWithPayload(groupPayload3), equalTo(3));
        assertThat(items.removeItemWithPayload(groupPayload1), equalTo(2));
        assertThat(items.removeItemWithPayload(groupPayload2), equalTo(2));
    }
}
