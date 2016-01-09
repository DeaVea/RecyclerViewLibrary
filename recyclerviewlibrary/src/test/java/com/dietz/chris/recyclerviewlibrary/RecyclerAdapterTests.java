package com.dietz.chris.recyclerviewlibrary;

import com.dietz.chris.recyclerviewlibrary.mocks.TestItem;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 *
 */
public class RecyclerAdapterTests {

    @Test
    public void addItemTests() {
        final RecyclerAdapter<TestItem> adapter = new RecyclerAdapter<>(null);
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
        final RecyclerAdapter<TestItem> adapter = new RecyclerAdapter<>(null);
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
        final RecyclerAdapter<TestItem> adapter = new RecyclerAdapter<>(null);
        adapter.addItem(new TestItem("Obj1"));
        adapter.addItem(new TestItem("Obj2"));
        adapter.addItem(new TestItem("Obj3"));
        adapter.addItem(new TestItem("Obj4"));
        adapter.addItem(new TestItem("Obj5"));
        assertThat(adapter.getItemCount(), is(equalTo(5)));

        adapter.clear();
        assertThat(adapter.getItemCount(), is(equalTo(0)));
    }
}
