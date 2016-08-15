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

import com.dietz.chris.recyclerviewlibrary.mocks.OrderTestItem;
import com.dietz.chris.recyclerviewlibrary.mocks.TestItem;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 *
 */
public class AdapterGroupTests {

    @Test
    public void testAdd() {
        final AdapterItemGroup group = new AdapterItemGroup<>(new TestItem("Group 0"));
        assertThat(group.getItemCount(), equalTo(1));
        AdapterItem sameItem = group.getItem(0);
        assertThat(sameItem == group, equalTo(true));

        AdapterItem newItem;
        group.addOrUpdateItem(newItem = new AdapterItem<>(new TestItem("Item 1")));
        assertThat(group.getItemCount(), equalTo(2));
        sameItem = group.getItem(0);
        assertThat(sameItem == group, equalTo(true));
        assertThat(newItem == group.getItem(1), equalTo(true));

        group.addOrUpdateItem(newItem = new AdapterItem<>(new TestItem("Item 3")));
        assertThat(group.getItemCount(), equalTo(3));
        sameItem = group.getItem(0);
        assertThat(sameItem == group, equalTo(true));
        assertThat(newItem == group.getItem(2), equalTo(true));


        group.addOrUpdateItem(new AdapterItem<>(new TestItem("Item 5")));
        group.addOrUpdateItem(new AdapterItem<>(new TestItem("Item 7")));
        group.addOrUpdateItem(new AdapterItem<>(new TestItem("Item 9")));

        assertThat(group.getItemCount(), equalTo(6));

        group.addOrUpdateItem(newItem = new AdapterItem<>(new TestItem("Item 0")));
        assertThat(newItem == group.getItem(1), equalTo(true));
        group.addOrUpdateItem(newItem = new AdapterItem<>(new TestItem("Item 2")));
        assertThat(newItem == group.getItem(3), equalTo(true));
        group.addOrUpdateItem(newItem = new AdapterItem<>(new TestItem("Item 4")));
        assertThat(newItem == group.getItem(5), equalTo(true));
        group.addOrUpdateItem(newItem = new AdapterItem<>(new TestItem("Item 6")));
        assertThat(newItem == group.getItem(7), equalTo(true));
        group.addOrUpdateItem(newItem = new AdapterItem<>(new TestItem("Item 8")));
        assertThat(newItem == group.getItem(9), equalTo(true));
        group.addOrUpdateItem(newItem = new AdapterItem<>(new TestItem("Item 99")));
        assertThat(newItem == group.getItem(11), equalTo(true));
    }

    @Test
    public void testUpdate() {
        final AdapterItemGroup group = new AdapterItemGroup<>(new TestItem("Group 0"));
        AdapterItem newItem;
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 0", 1)));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 1", 3)));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 2", 5)));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 3", 7)));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 4", 9)));
        assertThat(group.getItemCount(), equalTo(6));

        group.addOrUpdateItem(newItem = new AdapterItem<>(new OrderTestItem("Item 0", 4)));
        assertThat(group.getItemCount(), equalTo(6));
        assertThat(group.getItem(2) == newItem, equalTo(true));
    }

    @Test
    public void testOpenClose() {
        final AdapterItemGroup group = new AdapterItemGroup<>(new TestItem("Group 0"));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 0", 1)));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 1", 3)));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 2", 5)));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 3", 7)));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 4", 9)));
        assertThat(group.getItemCount(), equalTo(6));

        group.close();
        assertThat(group.getItemCount(), equalTo(1));
        group.open();
        assertThat(group.getItemCount(), equalTo(6));
    }

    @Test
    public void testAddOrUpdateCollections() {
        final AdapterItemGroup<TestItem> group = new AdapterItemGroup<>(new TestItem("Group 0"));
        final Collection<AdapterItem> newItem = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            newItem.add(new AdapterItem<>(new OrderTestItem("Item "  + i, i + 1)));
        }
        group.addOrUpdateItems(newItem);
        assertThat(group.getItemCount(), equalTo(51));

        // They should all be in order.
        for (int i = 1; i < group.getItemCount(); i++) {
            if (i == 50) {
                assertThat(true, is(equalTo(true)));
            }
            assertThat("Item at " + i + " is null.", group.getItem(i), is(notNullValue()));
            assertThat("Item at " + i + " does not have a payload.", group.getItem(i).getPayload(), is(notNullValue()));
            assertThat("Item at " + i + " is not an ordertestitem.", group.getItem(i).getPayload(), is(instanceOf(OrderTestItem.class)));
            final OrderTestItem item = (OrderTestItem) group.getItem(i).getPayload();
            assertThat("Item at " + i + " is the wrong identity key. Expected: Item " + (i - 1) + "; actual: " + item.getIdentityKey(), item.getIdentityKey(), is(equalTo("Item " + (i - 1))));
            assertThat("Item at " + i + " does not have the correct order. Expected: " + i + "; actual: " + item.getOrder(), item.getOrder(), is(equalTo(i)));
        }
    }

    @Test
    public void testAddOrUpdatePayloads() {
        final AdapterItemGroup<TestItem> group = new AdapterItemGroup<>(new TestItem("Group 0"));
        final Collection<OrderTestItem> newItem = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            newItem.add(new OrderTestItem("Item " + i, i + 1));
        }

        group.addOrUpdatePayloads(newItem);
        assertThat(group.getItemCount(), equalTo(51));

        // They should all be in order.
        for (int i = 1; i < group.getItemCount(); i++) {
            if (i == 50) {
                assertThat(true, is(equalTo(true)));
            }
            assertThat("Item at " + i + " is null.", group.getItem(i), is(notNullValue()));
            assertThat("Item at " + i + " does not have a payload.", group.getItem(i).getPayload(), is(notNullValue()));
            assertThat("Item at " + i + " is not an ordertestitem.", group.getItem(i).getPayload(), is(instanceOf(OrderTestItem.class)));
            final OrderTestItem item = (OrderTestItem) group.getItem(i).getPayload();
            assertThat("Item at " + i + " is the wrong identity key. Expected: Item " + (i - 1) + "; actual: " + item.getIdentityKey(), item.getIdentityKey(), is(equalTo("Item " + (i - 1))));
            assertThat("Item at " + i + " does not have the correct order. Expected: " + i + "; actual: " + item.getOrder(), item.getOrder(), is(equalTo(i)));
        }
    }

    @Test
    public void testFilter() {
        final AdapterItemGroup group = new AdapterItemGroup<>(new TestItem("Group 0"));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 0", 1)));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 1", 3)));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 2", 5)));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 3", 7)));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 4", 9)));

        assertThat(group.getItemCount(), is(6));

        group.filter(new Filter<OrderTestItem>() {
            @Override
            public boolean accept(OrderTestItem value) {
                return value.getIdentityKey().contains("1");
            }
        }, OrderTestItem.class);

        assertThat(group.getItemCount(), is(2));

        group.filter(new Filter<TestItem>() {
            @Override
            public boolean accept(TestItem value) {
                return !value.getIdentityKey().contains("0");
            }
        }, TestItem.class);

        assertThat(group.getItemCount(), is(0));
        assertThat(group.isHidden(), is(true));

        group.filter(null, OrderTestItem.class);

        assertThat(group.getItemCount(), is(0)); // Full group is still hidden.
        assertThat(group.isHidden(), is(true));

        group.filter(null, TestItem.class);
        assertThat(group.getItemCount(), is(6));
        assertThat(group.isHidden(), is(false));
    }

    @Test
    public void testGetItemsOfClass() {
        final AdapterItemGroup group = new AdapterItemGroup<>(new TestItem("Group 0"));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 0", 1)));
        group.addOrUpdateItem(new AdapterItem<>(new TestItem("Item 1")));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 2", 5)));
        group.addOrUpdateItem(new AdapterItem<>(new TestItem("Item 3")));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 4", 9)));
        group.addOrUpdateItem(new AdapterItem<>(new OrderTestItem("Item 5", 9)));

        Collection<TestItem> testItems = group.getItemsOfType(TestItem.class);
        assertThat(testItems.size(), is(3));

        Collection<TestItem> orderedItems = group.getItemsOfType(OrderTestItem.class);
        assertThat(orderedItems.size(), is(4));
    }
}
