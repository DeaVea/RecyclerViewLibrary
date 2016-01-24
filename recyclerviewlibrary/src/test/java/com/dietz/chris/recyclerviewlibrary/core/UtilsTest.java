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

import com.dietz.chris.recyclerviewlibrary.mocks.TestItem;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class UtilsTest {

    @Test
    public void getPositionInList() {
        final List<AdapterItem> items = new ArrayList<>();

        int position = Utils.getPosition(new AdapterItem<>(new TestItem("A")), items);
        assertThat(position, equalTo(0));

        items.add(new AdapterItem<>(new TestItem("B")));
        items.add(new AdapterItem<>(new TestItem("D")));
        items.add(new AdapterItem<>(new TestItem("E")));
        items.add(new AdapterItem<>(new TestItem("F")));
        items.add(new AdapterItem<>(new TestItem("J")));
        items.add(new AdapterItem<>(new TestItem("M")));
        items.add(new AdapterItem<>(new TestItem("P")));
        items.add(new AdapterItem<>(new TestItem("Q")));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("A")), items);
        assertThat(position, equalTo(0));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("B")), items);
        assertThat(position, equalTo(0));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("C")), items);
        assertThat(position, equalTo(1));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("D")), items);
        assertThat(position, equalTo(1));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("H")), items);
        assertThat(position, equalTo(4));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("J")), items);
        assertThat(position, equalTo(4));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("Z")), items);
        assertThat(position, equalTo(8));
    }
    
    @Test
    public void testGetPositionInListWithGroups() {
        final AdapterItemGroup group1 = new AdapterItemGroup<>(new TestItem("B"));
        group1.addOrUpdateItem(new AdapterItem<>(new TestItem(("A"))));
        group1.addOrUpdateItem(new AdapterItem<>(new TestItem(("B"))));
        group1.addOrUpdateItem(new AdapterItem<>(new TestItem(("C"))));
        final AdapterItemGroup group2 = new AdapterItemGroup<>(new TestItem("D"));
        group2.addOrUpdateItem(new AdapterItem<>(new TestItem(("A"))));
        group2.addOrUpdateItem(new AdapterItem<>(new TestItem(("B"))));
        group2.addOrUpdateItem(new AdapterItem<>(new TestItem(("C"))));
        final AdapterItemGroup group3 = new AdapterItemGroup<>(new TestItem("F"));
        group3.addOrUpdateItem(new AdapterItem<>(new TestItem(("A"))));
        group3.addOrUpdateItem(new AdapterItem<>(new TestItem(("B"))));
        group3.addOrUpdateItem(new AdapterItem<>(new TestItem(("C"))));

        final List<AdapterItem> items = new ArrayList<>();
        items.add(group1);
        items.add(group2);
        items.add(group3);
        
        int position = Utils.getPosition(new AdapterItem<>(new TestItem("A")), items);
        assertThat(position, equalTo(0));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("C")), items);
        assertThat(position, equalTo(4));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("E")), items);
        assertThat(position, equalTo(8));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("Z")), items);
        assertThat(position, equalTo(12));
    }

    @Test
    public void equalsTest(){
        TestItem payload1 = new TestItem("A");
        TestItem payload2 = new TestItem("B");
        TestItem payload3 = new TestItem("A");

        assertThat(Utils.itemsEqual(null, null), equalTo(true));
        assertThat(Utils.itemsEqual(payload1, null), equalTo(false));
        assertThat(Utils.itemsEqual(null, payload1), equalTo(false));
        assertThat(Utils.itemsEqual(payload1, payload1), equalTo(true));
        assertThat(Utils.itemsEqual(payload1, payload2), equalTo(false));
        assertThat(Utils.itemsEqual(payload1, payload3), equalTo(true));
        assertThat(Utils.itemsEqual(payload3, payload1), equalTo(true));
    }
}
