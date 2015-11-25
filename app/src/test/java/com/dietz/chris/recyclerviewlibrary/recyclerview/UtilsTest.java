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
    
    @Test
    public void testGetPositionInListWithGroups() {
        final TestGroupItem group1 = new TestGroupItem("B");
        group1.addOrUpdateItem(new TestItem("A"));
        group1.addOrUpdateItem(new TestItem("B"));
        group1.addOrUpdateItem(new TestItem("C"));
        final TestGroupItem group2 = new TestGroupItem("D");
        group2.addOrUpdateItem(new TestItem("A"));
        group2.addOrUpdateItem(new TestItem("B"));
        group2.addOrUpdateItem(new TestItem("C"));
        final TestGroupItem group3 = new TestGroupItem("F");
        group3.addOrUpdateItem(new TestItem("A"));
        group3.addOrUpdateItem(new TestItem("B"));
        group3.addOrUpdateItem(new TestItem("C"));

        final List<AdapterItem> items = new ArrayList<>();
        items.add(group1);
        items.add(group2);
        items.add(group3);
        
        int position = Utils.getPosition(new TestItem("A"), items);
        assertThat(position, equalTo(0));

        position = Utils.getPosition(new TestItem("C"), items);
        assertThat(position, equalTo(4));

        position = Utils.getPosition(new TestItem("E"), items);
        assertThat(position, equalTo(8));

        position = Utils.getPosition(new TestItem("Z"), items);
        assertThat(position, equalTo(12));
        
    }
}
