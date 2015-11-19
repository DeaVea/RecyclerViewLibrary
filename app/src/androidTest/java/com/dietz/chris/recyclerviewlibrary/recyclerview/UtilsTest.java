package com.dietz.chris.recyclerviewlibrary.recyclerview;

import android.support.annotation.NonNull;


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

        int position = Utils.getPosition(new TestItem("0"), items);
        assertThat(position, equalTo(0));

        items.add(new TestItem("1"));
        items.add(new TestItem("3"));
        items.add(new TestItem("4"));
        items.add(new TestItem("5"));
        items.add(new TestItem("7"));
        items.add(new TestItem("8"));
        items.add(new TestItem("9"));
        items.add(new TestItem("10"));

        position = Utils.getPosition(new TestItem("0"), items);
        assertThat(position, equalTo(0));

        position = Utils.getPosition(new TestItem("2"), items);
        assertThat(position, equalTo(1));

        position = Utils.getPosition(new TestItem("3"), items);
        assertThat(position, equalTo(1));

        position = Utils.getPosition(new TestItem("6"), items);
        assertThat(position, equalTo(4));

        position = Utils.getPosition(new TestItem("7"), items);
        assertThat(position, equalTo(4));

        position = Utils.getPosition(new TestItem("11"), items);
        assertThat(position, equalTo(8));
    }

    private static class TestItem implements AdapterItem {

        private final String key;

        public TestItem(String key) {
            this.key = key;
        }

        @NonNull
        @Override
        public String getIdentityKey() {
            return key;
        }

        @Override
        public int compareTo(@NonNull AdapterItem another) {
            return this.key.compareToIgnoreCase(another.getIdentityKey());
        }
    }
}
