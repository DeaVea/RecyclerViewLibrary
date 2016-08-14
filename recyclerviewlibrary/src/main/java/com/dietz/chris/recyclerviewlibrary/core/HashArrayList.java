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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 */
class HashArrayList<T extends Keyed> implements List<T> {

    private final ArrayList<T> mList;
    private final HashMap<String, T> mMap;

    public HashArrayList() {
        mList = new ArrayList<>();
        mMap = new HashMap<>();
    }

    /**
     * Adds the item at the location provided.  If the item contains the identity key of an item
     * already in the collection, then it will be replaced at the new location.
     * @param location
     *      Location to insert the item.
     * @param object
     *      Object to insert.
     */
    @Override
    public void add(int location, T object) {
        T oldObject = mMap.get(object.getIdentityKey());
        if (oldObject != null) {
            mList.remove(oldObject);
        }

        mList.add(location, object);
        mMap.put(object.getIdentityKey(), object);
    }

    /**
     * Adds the item to the location provided.  If the location is out of range, then it will adjust
     * and insert the item in the closest side. In other words, if location is negative, then
     * it will be placed at index 0.  If it is positive, it will be placed at index size - 1.
     *
     * @param location
     *      Index to place the item.
     * @param object
     *      Object to place.
     *
     * @return
     *      Actual index to put the item.
     */
    public int safeAdd(int location, T object) {
        final int addedIndex;
        if (location < 0) {
            addedIndex = 0;
            if (mList.isEmpty()) {
                add(object);
            } else {
                add(0, object);
            }
        } else if (location > mList.size()) {
            addedIndex = mList.size();
            add(object);
        } else {
            addedIndex = location;
            add(location, object);
        }

        return addedIndex;
    }

    /**
     * Adds the item to the the collection.
     *
     * *NOTE*:  If there are any duplicate items in this collection that contains the same identityKey,
     * then it will be replaced with the new item.
     *
     * @param object
     *      object to add to the collection.
     * @return
     *      True if the collection was changed as a result of the addition.
     */
    @Override
    public boolean add(T object) {
        if (object == null) {
            return false;
        }

        final boolean added;
        T oldItem = mMap.get(object.getIdentityKey());
        if (oldItem != null) {
            set(object);
            added = true;
        } else {
            added = mList.add(object);
            if (added) {
                mMap.put(object.getIdentityKey(), object);
            }
        }
        return added;
    }

    /**
     * Adds all in the collection.
     *
     * *NOTE*:  If there are any duplicate items in this collection that contains the same identityKey,
     * then it will be replaced with the new item.
     *
     * @param location
     *      Location to start inserting items.
     * @param collection
     *      Collection of items to insert.
     * @return
     *      True if the collection was changed as a result of the addition.
     */
    @Override
    public boolean addAll(int location, @NonNull Collection<? extends T> collection) {
        int count = location;

        for (T item : collection) {
            T oldItem = mMap.get(item.getIdentityKey());
            if (oldItem != null) {
                mList.remove(oldItem);
            }
            safeAdd(count, item);

            ++count;
        }
        return true;
    }

    /**
     * Adds all in the collection.
     *
     * *NOTE*:  If there are any duplicate items in this collection that contains the same identityKey,
     * then it will be replaced with the new item.
     *
     * @param collection
     *      Collection of items to insert.
     * @return
     *      True if the collection was changed as a result of the addition.
     */
    @Override
    public boolean addAll(@NonNull Collection<? extends T> collection) {
        for (T item : collection) {
            add(item);
        }
        return true;
    }

    @Override
    public void clear() {
        mList.clear();
        mMap.clear();
    }

    @Override
    public boolean contains(Object object) {
        return object != null && object instanceof AdapterItem && mMap.containsKey(((AdapterItem) object).getIdentityKey());
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        for (Object object : collection) {
            if (!contains(object)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object object) {
        return !(object == null || !(object instanceof HashArrayList)) && mMap.equals(((HashArrayList) object).mMap);
    }

    @Override
    public T get(int location) {
        return mList.get(location);
    }

    public T get(String key) {
        return mMap.get(key);
    }

    public T getReal(Object o) {
        if (o != null && o instanceof Keyed) {
            return mMap.get(((Keyed) o).getIdentityKey());
        }
        return null;
    }

    @Override
    public int hashCode() {
        return mList.hashCode() + mMap.hashCode();
    }

    @Override
    public int indexOf(Object object) {
        if (contains(object)) {
            //noinspection unchecked
            T oldItem = mMap.get(((T) object).getIdentityKey());
            return mList.indexOf(oldItem);
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return mList.isEmpty();
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return mList.iterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return mList.lastIndexOf(object);
    }

    @Override
    public ListIterator<T> listIterator() {
        return mList.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int location) {
        return mList.listIterator(location);
    }

    @Override
    public T remove(int location) {
        T obj = mList.remove(location);
        if (obj != null) {
            mMap.remove(obj.getIdentityKey());
        }
        return obj;
    }

    @Override
    public boolean remove(Object object) {
        T old = getReal(object);
        boolean removed = old != null;
        if (removed) {
            mList.remove(old);
            mMap.remove(old.getIdentityKey());
        }
        return removed;
    }

    public T remove(String key) {
        T old = mMap.remove(key);
        if (old != null) {
            mList.remove(old);
        }
        return old;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        boolean changed = false;
        for (Object obj : collection) {
            changed |= remove(obj);
        }
        return changed;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        throw new UnsupportedOperationException("Retain All is not supported yet.");
    }

    @Override
    public T set(int location, T object) {
        T oldItem = mMap.get(object.getIdentityKey());
        if (oldItem != null) {
            mList.set(location, object);
            mMap.put(object.getIdentityKey(), object);
        }
        return oldItem;
    }

    public T set(T object) {
        String identityKey = object.getIdentityKey();
        T oldItem = mMap.get(identityKey);
        if (oldItem != null) {
            int index = mList.indexOf(oldItem);
            mList.set(index, object);
            mMap.put(identityKey, object);
        }
        return oldItem;
    }

    @Override
    public int size() {
        return mList.size();
    }

    @NonNull
    @Override
    public List<T> subList(int start, int end) {
        return mList.subList(start, end);
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return mList.toArray();
    }

    @NonNull
    @Override
    public <T1> T1[] toArray(@NonNull T1[] array) {
        return mList.toArray(array);
    }
}
