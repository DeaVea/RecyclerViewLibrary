package com.dietz.chris.recyclerviewlibrary.recyclerview;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
public class AdapterItemTests {

    @Test
    public void testOpen() {
        final TestAdapterItemListener listener = new TestAdapterItemListener();
        final TestItem item = new TestItem("A");
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
        final TestItem item = new TestItem("A");

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
}
