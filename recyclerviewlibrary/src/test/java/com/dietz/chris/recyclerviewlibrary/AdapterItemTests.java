package com.dietz.chris.recyclerviewlibrary;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

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
}
