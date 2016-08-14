/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dietz.chris.recyclerviewlibrary.core;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
public class HashArrayListTests {

    private HashArrayList<KeyClass> list;

    private KeyClass class1;
    private KeyClass class2;
    private KeyClass class3;
    private KeyClass class4;
    private KeyClass class5;

    @Before
    public void before() {
        list = new HashArrayList<>();
        class1 = new KeyClass();
        class2 = new KeyClass();
        class3 = new KeyClass();
        class4 = new KeyClass();
        class5 = new KeyClass();
    }

    @Test
    public void addAndGetIndex() {
        assertThat(list.add(class1), is(true));
        assertThat(list.add(class2), is(true));
        assertThat(list.add(class3), is(true));

        assertThat(list.get(0), is(sameInstance(class1)));
        assertThat(list.get(1), is(sameInstance(class2)));
        assertThat(list.get(2), is(sameInstance(class3)));
    }

    @Test
    public void addNull() {
        assertThat(list.add(null), is(false));
    }

    @Test
    public void addAtLocationAndGetIndex() {
        list.add(class1);
        list.add(class2);
        list.add(class3);

        list.add(0, class4);
        list.add(2, class5);

        assertThat(list.get(0), is(sameInstance(class4)));
        assertThat(list.get(1), is(sameInstance(class1)));
        assertThat(list.get(2), is(sameInstance(class5)));
        assertThat(list.get(3), is(sameInstance(class2)));
        assertThat(list.get(4), is(sameInstance(class3)));
    }

    @Test
    public void replaceAdd() {
        list.add(class1);
        list.add(class2);
        list.add(class3);

        KeyClass newClass1 = new KeyClass(class1.getIdentityKey());
        KeyClass newClass2 = new KeyClass(class2.getIdentityKey());
        KeyClass newClass3 = new KeyClass(class3.getIdentityKey());

        assertThat(list.add(newClass1), is(true));
        assertThat(list.add(newClass2), is(true));
        assertThat(list.add(newClass3), is(true));

        assertThat(list.get(0), is(sameInstance(newClass1)));
        assertThat(list.get(1), is(sameInstance(newClass2)));
        assertThat(list.get(2), is(sameInstance(newClass3)));
    }

    @Test
    public void replaceAddAtLocation() {
        list.add(class1);
        list.add(class2);
        list.add(class3);
        list.add(class4);
        list.add(class5);

        KeyClass newClass1 = new KeyClass(class1.getIdentityKey());
        KeyClass newClass2 = new KeyClass(class2.getIdentityKey());
        KeyClass newClass3 = new KeyClass(class3.getIdentityKey());
        KeyClass newClass4 = new KeyClass(class4.getIdentityKey());
        KeyClass newClass5 = new KeyClass(class5.getIdentityKey());

        list.add(0, newClass5);
        list.add(1, newClass3);
        list.add(2, newClass1);
        list.add(3, newClass2);
        list.add(4, newClass4);

        assertThat(list.get(0), is(sameInstance(newClass5)));
        assertThat(list.get(1), is(sameInstance(newClass3)));
        assertThat(list.get(2), is(sameInstance(newClass1)));
        assertThat(list.get(3), is(sameInstance(newClass2)));
        assertThat(list.get(4), is(sameInstance(newClass4)));
    }

    @Test
    public void safeAdd() {
        assertThat(list.safeAdd(1, class1), is(0));
        assertThat(list.safeAdd(-1, class2), is(0));
        assertThat(list.safeAdd(5, class3), is(2));
        assertThat(list.safeAdd(1, class4), is(1));

        assertThat(list.get(0), is(sameInstance(class2)));
        assertThat(list.get(1), is(sameInstance(class4)));
        assertThat(list.get(2), is(sameInstance(class1)));
        assertThat(list.get(3), is(sameInstance(class3)));

        HashArrayList<KeyClass> newList = new HashArrayList<>();
        assertThat(newList.safeAdd(-1, class1), is(0));

        assertThat(newList.get(0), is(sameInstance(class1)));
    }

    @Test
    public void addAll() {
        ArrayList<KeyClass> newList = new ArrayList<>();
        newList.add(class1);
        newList.add(class2);
        newList.add(class3);
        newList.add(class4);
        newList.add(class5);

        list.addAll(newList);

        assertThat(list.get(0), is(sameInstance(class1)));
        assertThat(list.get(1), is(sameInstance(class2)));
        assertThat(list.get(2), is(sameInstance(class3)));
        assertThat(list.get(3), is(sameInstance(class4)));
        assertThat(list.get(4), is(sameInstance(class5)));
    }

    @Test
    public void addAllLocation() {
        ArrayList<KeyClass> newList = new ArrayList<>();
        newList.add(class1);
        newList.add(class2);

        list.addAll(newList);

        newList.clear();
        newList.add(class3);
        newList.add(class4);

        list.addAll(0, newList);

        newList.clear();

        KeyClass newClass6 = new KeyClass();

        newList.add(class5);
        newList.add(newClass6);

        list.addAll(2, newList);

        assertThat(list.get(0), is(sameInstance(class3)));
        assertThat(list.get(1), is(sameInstance(class4)));
        assertThat(list.get(2), is(sameInstance(class5)));
        assertThat(list.get(3), is(sameInstance(newClass6)));
        assertThat(list.get(4), is(sameInstance(class1)));
        assertThat(list.get(5), is(sameInstance(class2)));
    }

    @Test
    public void addAllReplace() {
        ArrayList<KeyClass> newList = new ArrayList<>();
        newList.add(class1);
        newList.add(class2);
        newList.add(class3);
        newList.add(class4);
        newList.add(class5);

        list.addAll(newList);

        newList.clear();

        KeyClass newClass1 = new KeyClass(class1.getIdentityKey());
        KeyClass newClass2 = new KeyClass(class2.getIdentityKey());
        KeyClass newClass3 = new KeyClass(class3.getIdentityKey());
        KeyClass newClass4 = new KeyClass(class4.getIdentityKey());
        KeyClass newClass5 = new KeyClass(class5.getIdentityKey());

        newList.add(newClass1);
        newList.add(newClass2);
        newList.add(newClass3);
        newList.add(newClass4);
        newList.add(newClass5);

        list.addAll(newList);

        assertThat(list.get(0), is(sameInstance(newClass1)));
        assertThat(list.get(1), is(sameInstance(newClass2)));
        assertThat(list.get(2), is(sameInstance(newClass3)));
        assertThat(list.get(3), is(sameInstance(newClass4)));
        assertThat(list.get(4), is(sameInstance(newClass5)));
    }

    private static class KeyClass implements Keyed {
        private static int count;

        private final String key;

        public KeyClass() {
            this(String.valueOf(count));
        }

        public KeyClass(String key) {
            this.key = key;
            ++count;
        }

        @NonNull
        @Override
        public String getIdentityKey() {
            return key;
        }

        @Override
        public String toString() {
            return "Class: " + getClass().getSimpleName() + "\nKey: " + getIdentityKey();
        }
    }
}