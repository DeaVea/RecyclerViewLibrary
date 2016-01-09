package com.dietz.chris.recyclerviewlibrary;

import com.dietz.chris.recyclerviewlibrary.mocks.TestGroupItem;
import com.dietz.chris.recyclerviewlibrary.mocks.TestItem;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 *
 */
public class RecylerGroupAdapterTests {

    @Test
    public void addGroupTest() {
        final RecyclerGroupAdapter<TestGroupItem, TestItem> adapter = new RecyclerGroupAdapter<>(null);
        assertThat(adapter.getItemCount(), is(equalTo(0)));

        adapter.addItem(new TestGroupItem("Group1").setType(1));
        assertThat(adapter.getItemCount(), is(equalTo(1)));

        adapter.addItemToGroup(new TestGroupItem("Group1"), new TestItem("TestItem1").setType(2));
        assertThat(adapter.getItemCount(), is(equalTo(2)));

        assertThat(adapter.getItemViewType(0), is(equalTo(1)));
        assertThat(adapter.getItemViewType(1), is(equalTo(2)));
    }
}
