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

        int position = Utils.getPosition(new TestItem("A"), items);
        assertThat(position, equalTo(0));

        items.add(new TestItem("B"));
        items.add(new TestItem("D"));
        items.add(new TestItem("E"));
        items.add(new TestItem("F"));
        items.add(new TestItem("J"));
        items.add(new TestItem("M"));
        items.add(new TestItem("P"));
        items.add(new TestItem("Q"));

        position = Utils.getPosition(new TestItem("A"), items);
        assertThat(position, equalTo(0));

        position = Utils.getPosition(new TestItem("B"), items);
        assertThat(position, equalTo(0));

        position = Utils.getPosition(new TestItem("C"), items);
        assertThat(position, equalTo(1));

        position = Utils.getPosition(new TestItem("D"), items);
        assertThat(position, equalTo(1));

        position = Utils.getPosition(new TestItem("H"), items);
        assertThat(position, equalTo(4));

        position = Utils.getPosition(new TestItem("J"), items);
        assertThat(position, equalTo(4));

        position = Utils.getPosition(new TestItem("Z"), items);
        assertThat(position, equalTo(8));
    }
}
