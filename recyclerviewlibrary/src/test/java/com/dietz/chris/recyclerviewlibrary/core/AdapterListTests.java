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

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class AdapterListTests {

    @Test
    public void replace() {
        TestAdapterListListener listener = new TestAdapterListListener();
        AdapterList list = new AdapterList();
        list.setListListener(listener);

        list.addItem(new TestItem("Obj1"));
        list.addItem(new TestItem("Obj2"));
        list.addItem(new TestItem("Obj3"));
        list.addItem(new TestItem("Obj4"));
        list.addItem(new TestItem("Obj5"));
        list.addItem(new TestItem("Obj6"));

        list.replaceWithItems(Arrays.asList(new TestItem("Obj1"), new TestItem("Obj3"), new TestItem("Obj5")));

        assertThat(list.size(), is(3));

        assertThat(listener.itemsPosInserted.size(), is(6));
        assertThat(listener.itemsPosChanged.size(), is(0));
        assertThat(listener.itemsRemoved.size(), is(3));
    }
}
