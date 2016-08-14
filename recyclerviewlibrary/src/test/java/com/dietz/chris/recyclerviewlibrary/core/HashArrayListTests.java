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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
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
    public void getKey() {
        list.add(class1);
        list.add(class2);
        list.add(class3);

        assertThat(list.get(class1.getIdentityKey()), is(sameInstance(class1)));
        assertThat(list.get(class2.getIdentityKey()), is(sameInstance(class2)));
        assertThat(list.get(class3.getIdentityKey()), is(sameInstance(class3)));
    }

    @Test
    public void getReal() {
        list.add(class1);
        list.add(class2);
        list.add(class3);

        assertThat(list.getReal(new KeyClass(class1)), is(sameInstance(class1)));
        assertThat(list.getReal(new KeyClass(class2)), is(sameInstance(class2)));
        assertThat(list.getReal(new KeyClass(class3)), is(sameInstance(class3)));
        assertThat(list.getReal(new KeyClass(class4)), is(nullValue()));

        assertThat(list.getReal("notKeyed"), is(nullValue()));
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
    public void indexOf() {
        list.add(class1);
        list.add(class2);
        list.add(class3);
        list.add(class4);
        list.add(class5);

        assertThat(list.indexOf(class1), is(0));
        assertThat(list.indexOf(class2), is(1));
        assertThat(list.indexOf(class3), is(2));
        assertThat(list.indexOf(class4), is(3));
        assertThat(list.indexOf(class5), is(4));
        assertThat(list.indexOf("Not"), is(-1));
    }

    @Test
    public void lastIndexOf() {
        list.add(class1);
        list.add(class2);
        list.add(class3);
        list.add(class4);
        list.add(class5);

        assertThat(list.lastIndexOf(class1), is(0));
        assertThat(list.lastIndexOf(class2), is(1));
        assertThat(list.lastIndexOf(class3), is(2));
        assertThat(list.lastIndexOf(class4), is(3));
        assertThat(list.lastIndexOf(class5), is(4));
        assertThat(list.lastIndexOf("Not"), is(-1));
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
    public void addAllIndex() {
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

    @Test
    public void addAllIndexReplace() {
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

        newList.add(newClass2);
        newList.add(newClass1);

        list.addAll(0, newList);

        newList.clear();
        newList.add(newClass5);
        newList.add(newClass4);
        newList.add(newClass3);

        list.addAll(2, newList);

        assertThat(list.get(0), is(sameInstance(newClass2)));
        assertThat(list.get(1), is(sameInstance(newClass1)));
        assertThat(list.get(2), is(sameInstance(newClass5)));
        assertThat(list.get(3), is(sameInstance(newClass4)));
        assertThat(list.get(4), is(sameInstance(newClass3)));
    }

    @Test
    public void clear() {
        list.add(class1);
        list.add(class2);
        list.add(class3);
        list.add(class4);
        list.add(class5);

        assertThat(list.size(), is(5));

        list.clear();

        assertThat(list.size(), is(0));
        assertThat(list.contains(class1), is(false));
        assertThat(list.contains(class2), is(false));
        assertThat(list.contains(class3), is(false));
        assertThat(list.contains(class4), is(false));
        assertThat(list.contains(class5), is(false));
    }

    @Test
    public void size() {
        assertThat(list.isEmpty(), is(true));
        assertThat(list.size(), is(0));

        list.add(class1);
        assertThat(list.isEmpty(), is(false));
        assertThat(list.size(), is(1));

        list.add(class2);
        assertThat(list.isEmpty(), is(false));
        assertThat(list.size(), is(2));

        list.add(class3);
        assertThat(list.isEmpty(), is(false));
        assertThat(list.size(), is(3));

        list.add(class4);
        assertThat(list.isEmpty(), is(false));
        assertThat(list.size(), is(4));
    }

    @Test
    public void contains() {
        assertThat(list.contains(class1), is(false));

        list.add(class1);

        assertThat(list.contains(class1), is(true));

        assertThat(list.contains(null), is(false));

        assertThat(list.contains("NotKeyed"), is(false));
    }

    @Test
    public void containsAll() {
        assertThat(list.containsAll(new ArrayList<>()), is(true));

        list.add(class1);
        list.add(class2);
        list.add(class3);
        list.add(class4);

        ArrayList<Keyed> arrayList = new ArrayList<>();
        arrayList.add(class1);
        arrayList.add(class2);
        arrayList.add(class3);

        assertThat(list.containsAll(arrayList), is(true));

        arrayList.add(class5);

        assertThat(list.containsAll(arrayList), is(false));
    }

    @Test
    public void equals() {
        assertThat(list.equals(list), is(true));

        assertThat(list.equals(new HashArrayList<KeyClass>()), is(true));

        list.add(class1);
        list.add(class2);

        assertThat(list.equals(new HashArrayList<KeyClass>()), is(false));

        HashArrayList<Keyed> newList = new HashArrayList<>();
        newList.add(class1);
        newList.add(class2);

        assertThat(list.equals(newList), is(true));

        assertThat(list.equals(null), is(false));
    }

    @Test
    public void hashCodeTest() {
        assertThat(list.hashCode(), is(list.hashCode()));

        assertThat(list.hashCode(), is(new HashArrayList<>().hashCode()));

        list.add(class1);

        assertThat(list.hashCode(), is(not(new HashArrayList<>().hashCode())));
    }

    @Test
    public void iterator() {
        assertThat(list.iterator(), is(notNullValue()));
        assertThat(list.listIterator(), is(notNullValue()));

        list.add(class1);
        list.add(class2);
        list.add(class3);

        assertThat(list.listIterator(2), is(notNullValue()));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeIndex() {
        list.add(class1);
        list.add(class2);
        list.add(class3);
        list.add(class4);
        list.add(class5);

        assertThat(list.remove(0), is(sameInstance(class1)));
        assertThat(list.remove(2), is(sameInstance(class4)));

        list.remove(5);
    }

    @Test
    public void removeObject() {
        list.add(class1);
        list.add(class2);
        list.add(class3);
        list.add(class4);
        list.add(class5);

        assertThat(list.remove(new KeyClass(class1)), is(true));
        assertThat(list.remove(new KeyClass(class3)), is(true));
        assertThat(list.remove(new KeyClass(class5)), is(true));
        assertThat(list.remove(new KeyClass("Not")), is(false));
    }

    @Test
    public void removeKey() {
        list.add(class1);
        list.add(class2);
        list.add(class3);
        list.add(class4);
        list.add(class5);

        assertThat(list.remove(class1.getIdentityKey()), is(class1));
        assertThat(list.remove(class3.getIdentityKey()), is(class3));
        assertThat(list.remove(class5.getIdentityKey()), is(class5));
    }

    @Test
    public void removeAll() {
        list.add(class1);
        list.add(class2);
        list.add(class3);
        list.add(class4);
        list.add(class5);

        ArrayList reallist = new ArrayList(Arrays.asList(new KeyClass(class1), new KeyClass(class3), new KeyClass(class5), new KeyClass("Not")));

        list.removeAll(reallist);

        assertThat(list.contains(class1), is(false));
        assertThat(list.contains(class3), is(false));
        assertThat(list.contains(class5), is(false));
        assertThat(list.contains(class2), is(true));
        assertThat(list.contains(class4), is(true));
    }

    @Test
    public void sublist() {
        list.add(class1);
        list.add(class2);
        list.add(class3);
        list.add(class4);
        list.add(class5);

        List<KeyClass> sublist = list.subList(1, 3);
        assertThat(sublist.size(), is(2));

        assertThat(sublist.get(0), is(class2));
        assertThat(sublist.get(1), is(class3));
    }

    @Test
    public void toArray() {
        list.add(class1);
        list.add(class2);
        list.add(class3);
        list.add(class4);
        list.add(class5);

        Object[] array = list.toArray();

        assertThat(array, is(notNullValue()));
        assertThat(array.length, is(5));
        assertThat(array[0], is(instanceOf(KeyClass.class)));
        assertThat((KeyClass) array[0], is(sameInstance(class1)));
        assertThat(array[1], is(instanceOf(KeyClass.class)));
        assertThat((KeyClass) array[1], is(sameInstance(class2)));
        assertThat(array[2], is(instanceOf(KeyClass.class)));
        assertThat((KeyClass) array[2], is(sameInstance(class3)));
        assertThat(array[3], is(instanceOf(KeyClass.class)));
        assertThat((KeyClass) array[3], is(sameInstance(class4)));
        assertThat(array[4], is(instanceOf(KeyClass.class)));
        assertThat((KeyClass) array[4], is(sameInstance(class5)));
    }

    @Test
    public void toArrayAgain() {
        list.add(class1);
        list.add(class2);
        list.add(class3);
        list.add(class4);
        list.add(class5);

        KeyClass[] array = list.toArray(new KeyClass[list.size()]);
        assertThat(array, is(notNullValue()));
        assertThat(array.length, is(5));
        assertThat(array[0], is(instanceOf(KeyClass.class)));
        assertThat(array[0], is(sameInstance(class1)));
        assertThat(array[1], is(instanceOf(KeyClass.class)));
        assertThat(array[1], is(sameInstance(class2)));
        assertThat(array[2], is(instanceOf(KeyClass.class)));
        assertThat(array[2], is(sameInstance(class3)));
        assertThat(array[3], is(instanceOf(KeyClass.class)));
        assertThat(array[3], is(sameInstance(class4)));
        assertThat(array[4], is(instanceOf(KeyClass.class)));
        assertThat(array[4], is(sameInstance(class5)));
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

        public KeyClass(KeyClass keyClass) {
            this(keyClass.key);
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
