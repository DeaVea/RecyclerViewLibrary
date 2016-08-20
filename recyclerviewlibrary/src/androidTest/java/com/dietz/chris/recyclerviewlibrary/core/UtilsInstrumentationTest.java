package com.dietz.chris.recyclerviewlibrary.core;

import android.support.annotation.NonNull;

import com.dietz.chris.recyclerviewlibrary.RecyclerItem;
import com.dietz.chris.recyclerviewlibrary.TestItem;
import com.dietz.chris.recyclerviewlibrary.core.AdapterItem;
import com.dietz.chris.recyclerviewlibrary.core.Utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 */
public class UtilsInstrumentationTest {

    @Test
    public void getPositionInList() {
        final List<AdapterItem> items = new ArrayList<>();

        int position = Utils.getPosition(new AdapterItem<>(new TestItem("0")), items);
        assertThat(position, equalTo(0));

        items.add(new AdapterItem<>(new TestItem("1")));
        items.add(new AdapterItem<>(new TestItem("3")));
        items.add(new AdapterItem<>(new TestItem("4")));
        items.add(new AdapterItem<>(new TestItem("5")));
        items.add(new AdapterItem<>(new TestItem("7")));
        items.add(new AdapterItem<>(new TestItem("8")));
        items.add(new AdapterItem<>(new TestItem("9")));
        items.add(new AdapterItem<>(new TestItem("10")));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("0")), items);
        assertThat(position, equalTo(0));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("2")), items);
        assertThat(position, equalTo(1));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("3")), items);
        assertThat(position, equalTo(1));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("6")), items);
        assertThat(position, equalTo(4));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("7")), items);
        assertThat(position, equalTo(4));

        position = Utils.getPosition(new AdapterItem<>(new TestItem("11")), items);
        assertThat(position, equalTo(8));
    }
}
