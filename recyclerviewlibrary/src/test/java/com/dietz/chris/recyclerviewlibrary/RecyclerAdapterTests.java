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

package com.dietz.chris.recyclerviewlibrary;

import android.view.ViewGroup;

import com.dietz.chris.recyclerviewlibrary.core.AdapterItem;
import com.dietz.chris.recyclerviewlibrary.mocks.TestItem;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 *
 */
public class RecyclerAdapterTests {

    @Test
    public void setViewHolderFactoryTests() {
        final RecyclerAdapter adapter = new RecyclerAdapter(null);
        adapter.setViewHolderFactory(new ViewHolderFactory() {
            @Override
            public ViewHolder<? extends RecyclerItem> createViewHolder(ViewGroup parent, int type) {
                return null;
            }
        });
    }

    @Test
    public void addItemTests() {
        final RecyclerAdapter adapter = new RecyclerAdapter(null);
        assertThat(adapter.getItemCount(), is(equalTo(0)));
        adapter.addItem(new TestItem("Obj1").setType(1));
        assertThat(adapter.getItemCount(), is(equalTo(1)));
        adapter.addItem(new TestItem("Obj2").setType(2));
        assertThat(adapter.getItemCount(), is(equalTo(2)));

        assertThat(adapter.getItemViewType(0), is(equalTo(1)));
        assertThat(adapter.getItemViewType(1), is(equalTo(2)));

        adapter.addItem(null);
        assertThat(adapter.getItemCount(), is(equalTo(2)));
    }

    @Test
    public void removeItemTests() {
        final RecyclerAdapter adapter = new RecyclerAdapter(null);
        adapter.addItem(new TestItem("Obj1"));
        adapter.addItem(new TestItem("Obj2"));
        adapter.addItem(new TestItem("Obj3"));
        adapter.addItem(new TestItem("Obj4"));
        adapter.addItem(new TestItem("Obj5"));
        assertThat(adapter.getItemCount(), is(equalTo(5)));

        adapter.removeItem(new TestItem("Obj3"));
        assertThat(adapter.getItemCount(), is(equalTo(4)));
        adapter.removeItem(new TestItem("Obj4"));
        assertThat(adapter.getItemCount(), is(equalTo(3)));
        adapter.removeItem(new TestItem("Obj3"));
        assertThat(adapter.getItemCount(), is(equalTo(3)));
        adapter.removeItem(new TestItem("Obj2"));
        assertThat(adapter.getItemCount(), is(equalTo(2)));
        adapter.removeItem(new TestItem("Obj5"));
        assertThat(adapter.getItemCount(), is(equalTo(1)));
        adapter.removeItem(new TestItem("Obj1"));
        assertThat(adapter.getItemCount(), is(equalTo(0)));
        adapter.removeItem(new TestItem("Obj1"));
        assertThat(adapter.getItemCount(), is(equalTo(0)));

        adapter.removeItem(null); // Just ensuring it doesn't crash.
    }

    @Test
    public void clearTests() {
        final RecyclerAdapter adapter = new RecyclerAdapter(null);
        adapter.addItem(new TestItem("Obj1"));
        adapter.addItem(new TestItem("Obj2"));
        adapter.addItem(new TestItem("Obj3"));
        adapter.addItem(new TestItem("Obj4"));
        adapter.addItem(new TestItem("Obj5"));
        assertThat(adapter.getItemCount(), is(equalTo(5)));

        adapter.clear();
        assertThat(adapter.getItemCount(), is(equalTo(0)));
    }
    
    @Test
    public void getPayloadTests() {
        final RecyclerAdapter adapter = new RecyclerAdapter(null);
        adapter.addItem(new TestItem("Obj1").setValue(1));
        adapter.addItem(new TestItem("Obj2").setValue(2));
        adapter.addItem(new TestItem("Obj3").setValue(3));
        adapter.addItem(new TestItem("Obj4").setValue(4));
        adapter.addItem(new TestItem("Obj5").setValue(5));
        
        RecyclerItem item = adapter.findItem("Obj1");
        assertThat(item, is(notNullValue()));
        assertThat(item instanceof TestItem, is(equalTo(true)));
        
        TestItem actualItem = adapter.findItem("Obj1");
        assertThat(actualItem.getValue(), is(equalTo(1)));
        actualItem = adapter.findItem("Obj2");
        assertThat(actualItem.getValue(), is(equalTo(2)));
        actualItem = adapter.findItem("Obj3");
        assertThat(actualItem.getValue(), is(equalTo(3)));
        actualItem = adapter.findItem("Obj4");
        assertThat(actualItem.getValue(), is(equalTo(4)));
        actualItem = adapter.findItem("Obj5");
        assertThat(actualItem.getValue(), is(equalTo(5)));

        actualItem = adapter.findItem("Obj6");
        assertThat(actualItem, is(nullValue()));
        actualItem = adapter.findItem(null);
        assertThat(actualItem, is(nullValue()));
    }

    @Test
    public void getAdapterItemTests() {
        final RecyclerAdapter adapter = new RecyclerAdapter(null);
        adapter.addItem(new TestItem("Obj1").setValue(1));
        adapter.addItem(new TestItem("Obj2").setValue(2));
        adapter.addItem(new TestItem("Obj3").setValue(3));
        adapter.addItem(new TestItem("Obj4").setValue(4));
        adapter.addItem(new TestItem("Obj5").setValue(5));

        AdapterItem adapterItem = adapter.findAdapterItem("Obj1");
        assertThat(adapter, is(notNullValue()));
        assertThat(adapterItem.getPayload(), is(notNullValue()));
        assertThat(adapterItem.getPayload() instanceof TestItem, is(equalTo(true)));

        AdapterItem<TestItem> testAdapterItem = adapter.findAdapterItem("Obj1");
        TestItem payload = testAdapterItem.getPayload();
        assertThat(payload.getValue(), is(equalTo(1)));
        testAdapterItem = adapter.findAdapterItem("Obj2");
        payload = testAdapterItem.getPayload();
        assertThat(payload.getValue(), is(equalTo(2)));
        testAdapterItem = adapter.findAdapterItem("Obj3");
        payload = testAdapterItem.getPayload();
        assertThat(payload.getValue(), is(equalTo(3)));
        testAdapterItem = adapter.findAdapterItem("Obj4");
        payload = testAdapterItem.getPayload();
        assertThat(payload.getValue(), is(equalTo(4)));
        testAdapterItem = adapter.findAdapterItem("Obj5");
        payload = testAdapterItem.getPayload();
        assertThat(payload.getValue(), is(equalTo(5)));

        testAdapterItem = adapter.findAdapterItem("Obj6");
        assertThat(testAdapterItem, is(nullValue()));
    }

    @Test
    public void addToAdapterItemTests() {
        final RecyclerAdapter adapter = new RecyclerAdapter(null);
        adapter.addItem(new TestItem("Obj1").setValue(1));
        adapter.addItem(new TestItem("Obj2").setValue(2));
        adapter.addItem(new TestItem("Obj3").setValue(3));
        adapter.addItem(new TestItem("Obj4").setValue(4));
        adapter.addItem(new TestItem("Obj5").setValue(5));

        assertThat(adapter.getItemCount(), is(equalTo(5)));

        AdapterItem<TestItem> item = adapter.findAdapterItem("Obj1");
        assertThat(item.addOrUpdatePayload(new TestItem("SubObj1")), is(equalTo(true)));
        assertThat(adapter.getItemCount(), is(equalTo(6)));

        TestItem subItem = adapter.findItem("SubObj1");
        assertThat(subItem, is(notNullValue()));
    }

    @Test
    public void addItemsTests() {
        final RecyclerAdapter adapter = new RecyclerAdapter(null);
        List<TestItem> items = new ArrayList<>();
        items.add(new TestItem("Obj1"));
        items.add(new TestItem("Obj2"));
        items.add(new TestItem("Obj3"));
        items.add(new TestItem("Obj4"));
        items.add(new TestItem("Obj5"));

        adapter.addItems(items);

        assertThat(adapter.getItemCount(), is(equalTo(5)));

        items.add(new TestItem("Obj6"));
        items.add(new TestItem("Obj7"));
        items.add(new TestItem("Obj8"));
        items.add(new TestItem("Obj9"));
        items.add(new TestItem("Obj10"));

        adapter.addItems(items);
        // The previous items would just be replaced.
        assertThat(adapter.getItemCount(), is(equalTo(10)));
    }

    @Test
    public void replaceAllItemsTests() {
        final RecyclerAdapter adapter = new RecyclerAdapter(null);
        adapter.addItem(new TestItem("Obj1"));
        adapter.addItem(new TestItem("Obj2"));
        adapter.addItem(new TestItem("Obj3"));
        adapter.addItem(new TestItem("Obj4"));
        adapter.addItem(new TestItem("Obj5"));
        adapter.addItem(new TestItem("Obj6"));

        assertThat(adapter.getItemCount(), is(6));

        Collection<RecyclerItem> itemsToReplace = new ArrayList<>(5);
        itemsToReplace.add(new TestItem("Obj1"));
        itemsToReplace.add(new TestItem("Obj3"));
        itemsToReplace.add(new TestItem("Obj5"));

        adapter.replaceAll(itemsToReplace);

        assertThat(adapter.getItemCount(), is(3));
        assertThat(adapter.findAdapterItem("Obj1"), is(notNullValue()));
        assertThat(adapter.findAdapterItem("Obj2"), is(nullValue()));
        assertThat(adapter.findAdapterItem("Obj3"), is(notNullValue()));
        assertThat(adapter.findAdapterItem("Obj4"), is(nullValue()));
        assertThat(adapter.findAdapterItem("Obj5"), is(notNullValue()));
        assertThat(adapter.findAdapterItem("Obj6"), is(nullValue()));

        adapter.clear();
        itemsToReplace.clear();
    }

    @Test
    public void replaceAllItemsTests2() {
        final RecyclerAdapter adapter = new RecyclerAdapter(null);
        adapter.addItem(new TestItem("Obj1"));
        adapter.addItem(new TestItem("Obj2"));
        adapter.addItem(new TestItem("Obj3"));
        adapter.addItem(new TestItem("Obj4"));
        adapter.addItem(new TestItem("Obj5"));
        adapter.addItem(new TestItem("Obj6"));

        Collection<RecyclerItem> itemsToReplace = new ArrayList<>(5);
        itemsToReplace.add(new TestItem("Obj7"));
        itemsToReplace.add(new TestItem("Obj8"));
        itemsToReplace.add(new TestItem("Obj9"));

        adapter.replaceAll(itemsToReplace);

        assertThat(adapter.getItemCount(), is(3));
        assertThat(adapter.findAdapterItem("Obj1"), is(nullValue()));
        assertThat(adapter.findAdapterItem("Obj2"), is(nullValue()));
        assertThat(adapter.findAdapterItem("Obj3"), is(nullValue()));
        assertThat(adapter.findAdapterItem("Obj4"), is(nullValue()));
        assertThat(adapter.findAdapterItem("Obj5"), is(nullValue()));
        assertThat(adapter.findAdapterItem("Obj6"), is(nullValue()));
        assertThat(adapter.findAdapterItem("Obj7"), is(notNullValue()));
        assertThat(adapter.findAdapterItem("Obj8"), is(notNullValue()));
        assertThat(adapter.findAdapterItem("Obj9"), is(notNullValue()));
    }
}
