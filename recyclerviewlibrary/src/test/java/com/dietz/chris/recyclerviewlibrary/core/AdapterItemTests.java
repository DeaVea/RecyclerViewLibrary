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

import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 *
 */
public class AdapterItemTests {

    @Test
    public void testOpen() {
        final TestAdapterItemListener listener = new TestAdapterItemListener();
        final AdapterItem item = new AdapterItem<>(new TestItem("A"));
        item.bindList(listener);

        assertThat(item.isOpen(), equalTo(true));
        item.open();
        assertThat(item.isOpen(), equalTo(true));
        assertThat(listener.mChangedItems.size(), equalTo(0));

        item.close();
        assertThat(item.isOpen(), equalTo(false));
        assertThat(listener.mChangedItems.size(), equalTo(1));

        item.close();
        assertThat(item.isOpen(), equalTo(false));
        assertThat(listener.mChangedItems.size(), equalTo(1));

        item.open();
        assertThat(item.isOpen(), equalTo(true));
        assertThat(listener.mChangedItems.size(), equalTo(2));
    }

    @Test
    public void testOpenLock() {
        final AdapterItem item = new AdapterItem<>(new TestItem("A"));

        assertThat(item.isOpen(), equalTo(true));

        item.allowOpenClose(false);

        assertThat(item.isOpen(), equalTo(true));

        item.close();

        assertThat(item.isOpen(), equalTo(true));

        item.allowOpenClose(true);

        item.close();

        assertThat(item.isOpen(), equalTo(false));

        item.allowOpenClose(false);

        assertThat(item.isOpen(), equalTo(false));

        item.open();

        assertThat(item.isOpen(), equalTo(false));
    }

    @Test
    public void testPayload() {
        TestItem payload1 = new TestItem("TestItem1");
        final AdapterItem item = new AdapterItem<>(new TestItem("TestItem1"));

        assertThat(item.getPayload(), notNullValue());
        assertThat(item.getIdentityKey(), notNullValue());
        assertThat(item.hasPayload(item.getPayload()), equalTo(true));

        final AdapterItemGroup group = new AdapterItemGroup<>(new TestItem("Group1"));
        assertThat(group.hasPayload(payload1), equalTo(false));

        group.addOrUpdateItem(item);

        assertThat(group.hasPayload(payload1), equalTo(true));
    }

    @Test
    public void filteredOpenNullFilter() {
        final AdapterItem item = new AdapterItem<>(new TestItem("TestItem1"));
        assertThat(item.filteredOpen(), is(true));
    }

    @Test
    public void hide() {
        // As I found earlier, hide should *not* effect the item count for the base adapter.  It is *always* one.
        final AdapterItem item = new AdapterItem<>(new TestItem("TestItem1"));
        assertThat(item.getItemCount(), is(1));
        item.hide();
        assertThat(item.getItemCount(), is(1));
        item.reveal();
        assertThat(item.getItemCount(), is(1));
    }

    @Test
    public void filteredClosedRightClass() {
        final AdapterItem<TestItem> item = new AdapterItem<>(new TestItem("TestItem1"));
        item.filter(new Filter<TestItem>() {
            @Override
            public boolean accept(TestItem value) {
                return false;
            }
        }, TestItem.class);

        assertThat(item.filteredOpen(), is(false));
        assertThat(item.isHidden(), is(true));
        assertThat(item.getItemCount(), is(1));

        item.reveal();

        assertThat(item.filteredOpen(), is(false));
        assertThat(item.isHidden(), is(true));
        assertThat(item.getItemCount(), is(1));

        item.filter(new Filter<TestItem>() {
            @Override
            public boolean accept(TestItem value) {
                return true;
            }
        }, TestItem.class);

        assertThat(item.filteredOpen(), is(true));
        assertThat(item.isHidden(), is(false));
        assertThat(item.getItemCount(), is(1));
    }

    @Test
    public void filteredOpenRightClass() {
        final AdapterItem<TestItem> item = new AdapterItem<>(new TestItem("TestItem1"));
        item.filter(new Filter<TestItem>() {
            @Override
            public boolean accept(TestItem value) {
                return true;
            }
        }, TestItem.class);

        assertThat(item.filteredOpen(), is(true));
        assertThat(item.getItemCount(), is(1));
    }

    @Test
    public void testGetItemsOfClass() {
        final AdapterItem<TestItem> item = new AdapterItem<>(new TestItem("TestItem1"));

        Collection<AdapterItem<TestItem>> getItems = item.getItemsOfType(TestItem.class);
        assertThat(getItems.size(), is(1));
        assertThat(getItems.iterator().next(), is(item));

        Collection<AdapterItem<OrderTestItem>> newItems = item.getItemsOfType(OrderTestItem.class);
        assertThat(newItems.isEmpty(), is(true));

    }
}
