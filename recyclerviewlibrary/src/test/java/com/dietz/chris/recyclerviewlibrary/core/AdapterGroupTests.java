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


        group.addOrUpdateItem(newItem = new AdapterItem<>(new TestItem("Item 5")));
        group.addOrUpdateItem(newItem = new AdapterItem<>(new TestItem("Item 7")));
        group.addOrUpdateItem(newItem = new AdapterItem<>(new TestItem("Item 9")));

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
        group.addOrUpdateItem(newItem = new AdapterItem<>(new OrderTestItem("Item 0", 1)));
        group.addOrUpdateItem(newItem = new AdapterItem<>(new OrderTestItem("Item 1", 3)));
        group.addOrUpdateItem(newItem = new AdapterItem<>(new OrderTestItem("Item 2", 5)));
        group.addOrUpdateItem(newItem = new AdapterItem<>(new OrderTestItem("Item 3", 7)));
        group.addOrUpdateItem(newItem = new AdapterItem<>(new OrderTestItem("Item 4", 9)));
        assertThat(group.getItemCount(), equalTo(6));

        group.addOrUpdateItem(newItem = new AdapterItem<>(new OrderTestItem("Item 0", 4)));
        assertThat(group.getItemCount(), equalTo(6));
        assertThat(group.getItem(2) == newItem, equalTo(true));
    }

    @Test
    public void testOpenClose() {
        final AdapterItemGroup group = new AdapterItemGroup<>(new TestItem("Group 0"));
        AdapterItem newItem;
        group.addOrUpdateItem(newItem = new AdapterItem<>(new OrderTestItem("Item 0", 1)));
        group.addOrUpdateItem(newItem = new AdapterItem<>(new OrderTestItem("Item 1", 3)));
        group.addOrUpdateItem(newItem = new AdapterItem<>(new OrderTestItem("Item 2", 5)));
        group.addOrUpdateItem(newItem = new AdapterItem<>(new OrderTestItem("Item 3", 7)));
        group.addOrUpdateItem(newItem = new AdapterItem<>(new OrderTestItem("Item 4", 9)));
        assertThat(group.getItemCount(), equalTo(6));

        group.close();
        assertThat(group.getItemCount(), equalTo(1));
        group.open();
        assertThat(group.getItemCount(), equalTo(6));
    }
}
