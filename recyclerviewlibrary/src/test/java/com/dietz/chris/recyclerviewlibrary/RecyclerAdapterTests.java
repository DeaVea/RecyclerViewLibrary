package com.dietz.chris.recyclerviewlibrary;

import com.dietz.chris.recyclerviewlibrary.core.AdapterItem;
import com.dietz.chris.recyclerviewlibrary.mocks.TestItem;

import org.junit.Test;

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
            public ViewHolder<? extends RecyclerItem> createViewHolder(int type) {
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
}
