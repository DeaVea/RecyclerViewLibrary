package com.dietz.chris.recyclerviewlibrary.recyclerview;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
public class DuelCollectionTests {

    @Test
    public void addAndSizeTests() {
        TestAdapterListListener listListener = new TestAdapterListListener();
        final AdapterItemCollection<TestItem> items = new AdapterItemCollection<>(listListener);

        assertThat(items.size(), equalTo(0));

        int position = items.addOrUpdate(new TestItem("C"));
        assertThat(position, equalTo(0));
        assertThat(items.size(), equalTo(1));
        assertThat(listListener.itemsPosInserted.size(), equalTo(1));
        assertThat(listListener.lastItemPositionInserted(), equalTo(0));

        position = items.addOrUpdate(new TestItem("A"));
        assertThat(position, equalTo(0));
        assertThat(items.size(), equalTo(2));
        assertThat(listListener.itemsPosInserted.size(), equalTo(2));
        assertThat(listListener.lastItemPositionInserted(), equalTo(0));

        position = items.addOrUpdate(new TestItem("B"));
        assertThat(position, equalTo(1));
        assertThat(items.size(), equalTo(3));
        assertThat(listListener.itemsPosInserted.size(), equalTo(3));
        assertThat(listListener.lastItemPositionInserted(), equalTo(1));

        position = items.addOrUpdate(new TestItem("D"));
        assertThat(position, equalTo(3));
        assertThat(items.size(), equalTo(4));
        assertThat(listListener.itemsPosInserted.size(), equalTo(4));
        assertThat(listListener.lastItemPositionInserted(), equalTo(3));
    }

    @Test
    public void updateTests() {
        TestAdapterListListener listListener = new TestAdapterListListener();
        final AdapterItemCollection<TestItem> items = new AdapterItemCollection<>(listListener);

        items.addOrUpdate(new OrderTestItem("A", 0));
        items.addOrUpdate(new OrderTestItem("B", 1));
        items.addOrUpdate(new OrderTestItem("C", 2));
        items.addOrUpdate(new OrderTestItem("D", 3));
        items.addOrUpdate(new OrderTestItem("E", 4));

        assertThat(items.size(), equalTo(5));

        int position = items.addOrUpdate(new OrderTestItem("A", 0));
        assertThat(position, equalTo(0));
        assertThat(listListener.itemsPosChanged.size(), equalTo(1));
        assertThat(listListener.lastItemPositionChanged(), equalTo(0));

        position = items.addOrUpdate(new OrderTestItem("B", 1));
        assertThat(position, equalTo(1));
        assertThat(listListener.itemsPosChanged.size(), equalTo(2));
        assertThat(listListener.lastItemPositionChanged(), equalTo(1));

        position = items.addOrUpdate(new OrderTestItem("C", 2));
        assertThat(position, equalTo(2));
        assertThat(listListener.itemsPosChanged.size(), equalTo(3));
        assertThat(listListener.lastItemPositionChanged(), equalTo(2));

        position = items.addOrUpdate(new OrderTestItem("D", 3));
        assertThat(position, equalTo(3));
        assertThat(listListener.itemsPosChanged.size(), equalTo(4));
        assertThat(listListener.lastItemPositionChanged(), equalTo(3));

        position = items.addOrUpdate(new OrderTestItem("E", 4));
        assertThat(position, equalTo(4));
        assertThat(listListener.itemsPosChanged.size(), equalTo(5));
        assertThat(listListener.lastItemPositionChanged(), equalTo(4));

        assertThat(items.size(), equalTo(5));

        position = items.addOrUpdate(new OrderTestItem("A", 1));
        assertThat(position, equalTo(0));
        assertThat(listListener.itemsMoved.size(), equalTo(0));
        assertThat(listListener.lastItemPositionChanged(), equalTo(0));

        position = items.addOrUpdate(new OrderTestItem("B", 0));
        assertThat(position, equalTo(0));
        assertThat(listListener.itemsMoved.size(), equalTo(1));
        assertThat(listListener.lastItemMoved().fromPosition, equalTo(1));
        assertThat(listListener.lastItemMoved().toPosition, equalTo(0));

        position = items.addOrUpdate(new OrderTestItem("C", 10));
        assertThat(position, equalTo(4));
        assertThat(listListener.itemsMoved.size(), equalTo(2));
        assertThat(listListener.lastItemMoved().fromPosition, equalTo(2));
        assertThat(listListener.lastItemMoved().toPosition, equalTo(4));

        position = items.addOrUpdate(new OrderTestItem("A", 7));
        assertThat(position, equalTo(3));
        assertThat(listListener.itemsMoved.size(), equalTo(3));
        assertThat(listListener.lastItemMoved().fromPosition, equalTo(1));
        assertThat(listListener.lastItemMoved().toPosition, equalTo(3));
    }

    @Test
    public void getTests() {
        TestAdapterListListener listListener = new TestAdapterListListener();
        final AdapterItemCollection<TestItem> items = new AdapterItemCollection<>(listListener);

        items.addOrUpdate(new OrderTestItem("A", 0));
        items.addOrUpdate(new OrderTestItem("B", 1));
        items.addOrUpdate(new OrderTestItem("C", 2));
        items.addOrUpdate(new OrderTestItem("D", 3));
        items.addOrUpdate(new OrderTestItem("E", 4));

        assertThat(items.get(0).getIdentityKey(), equalTo("A"));
        assertThat(items.get(1).getIdentityKey(), equalTo("B"));
        assertThat(items.get(2).getIdentityKey(), equalTo("C"));
        assertThat(items.get(3).getIdentityKey(), equalTo("D"));
        assertThat(items.get(4).getIdentityKey(), equalTo("E"));
    }

    @Test
    public void clearTests() {
        TestAdapterListListener listListener = new TestAdapterListListener();
        final AdapterItemCollection<TestItem> items = new AdapterItemCollection<>(listListener);

        items.addOrUpdate(new OrderTestItem("A", 0));
        items.addOrUpdate(new OrderTestItem("B", 1));
        items.addOrUpdate(new OrderTestItem("C", 2));
        items.addOrUpdate(new OrderTestItem("D", 3));
        items.addOrUpdate(new OrderTestItem("E", 4));

        assertThat(items.size(), equalTo(5));

        items.clear();

        assertThat(items.size(), equalTo(0));

        assertThat(listListener.itemsRangeRemoved.size(), equalTo(1));
        assertThat(listListener.lastItemRangeRemoved().startPosition, equalTo(0));
        assertThat(listListener.lastItemRangeRemoved().count, equalTo(5));
    }
}
