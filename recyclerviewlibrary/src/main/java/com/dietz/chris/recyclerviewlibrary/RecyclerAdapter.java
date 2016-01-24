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

package com.dietz.chris.recyclerviewlibrary;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dietz.chris.recyclerviewlibrary.core.AdapterItem;
import com.dietz.chris.recyclerviewlibrary.core.AdapterList;
import com.dietz.chris.recyclerviewlibrary.core.AdapterListListener;

import java.util.Collection;

/**
 *
 */
public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder<? extends RecyclerItem>> {

    private final AdapterList mList;
    private ViewHolderFactory mHolderFactory;

    public RecyclerAdapter() {
        mList = new AdapterList();
        mList.setListListener(new AdapterListListener(this));
    }

    /**
     * Testing constructor.  If there is no recyclerview attached to this adapter, then it will crash
     * when items are added (RecyclerView#notify_________ methods will crash).  This allows a custom
     * list adapter listener that's not the default one.
     * @param listListener
     *      Listlistner to use.
     */
    RecyclerAdapter(AdapterListListener listListener) {
        mList = new AdapterList();
        mList.setListListener(listListener);
    }

    /**
     * Returns the underlying List so inheriting classes can perform custom actions.  Do not make this public
     * Internal uses only.
     * @return
     *      The list that backs this adapter.
     */
    AdapterList getList() {
        return mList;
    }

    /**
     * Sets a holder factory to create the {@link ViewHolder} that backs this adapter.
     *
     * This is required to be set before using the adapter.
     * @param factory
     *      Viewholder factory that must be set before coming to view.
     *
     */
    public void setViewHolderFactory(@Nullable ViewHolderFactory factory) {
        if (factory == null) {
            throw new IllegalArgumentException("ViewHolderFactory can not be null.");
        }
        mHolderFactory = factory;
    }

    /**
     * Add a an item to the adapter.
     */
    public void addItem(@Nullable RecyclerItem item) {
        if (item != null) {
            mList.addItem(item);
        }
    }

    /**
     * Adds and sorts the collection of items in to the adapter.
     * @param items
     *      Items to add.
     */
    public void addItems(@Nullable Collection<? extends RecyclerItem> items) {
        if (items != null && !items.isEmpty()) {
            // TODO: Issue is they may not be in the proper order, so need to insert them individually to sort.  Need a better way.
            for (RecyclerItem item : items) {
                addItem(item);
            }
        }
    }

    /**
     * Removes an item found in the list.  This will check the underlying key for the item, so they
     * may not be perfectly equal.
     */
    public void removeItem(@Nullable RecyclerItem item) {
        if (item != null) {
            mList.removeItem(item);
        }
    }

    /**
     * Returns the first item found with the given identity key or null if the item was not found.
     * @param identityKey
     *      Identity key of the item found.
     * @return
     *      Payload at the given ID or null if it was not found.
     */
    public <T extends RecyclerItem> T findItem(String identityKey) {
        return mList.findPayloadWithId(identityKey);
    }

    /**
     * Returns the first adapter item found with the given identity key.  This will throw a
     * "ClassCastException" if the payload with the identity key is different then what was expected.
     * @param identityKey
     *      Identity key of the item found.
     * @return
     *      Adapter item which is backing this adapter.
     */
    public <K extends RecyclerItem> AdapterItem<K> findAdapterItem(String identityKey) {
        //noinspection unchecked  It's supposed to throw a ClassCastException which means the caller screwed up.
        return mList.findItemWithId(identityKey);
    }

    /**
     * Clears the entire list.
     */
    public void clear() {
        mList.clear();
    }

    @Override
    public final ViewHolder<? extends RecyclerItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHolderFactory == null) {
            throw new IllegalStateException(getClass().getCanonicalName() + " does not have a " + ViewHolderFactory.class.getCanonicalName() + " set. Please call setViewHolder(ViewHolderFactory)");
        }
        return mHolderFactory.createViewHolder(viewType);
    }

    @Override
    public final void onBindViewHolder(ViewHolder holder, int position) {
        //noinspection unchecked  The item will be the appropriate class if the types are all well and good.
        holder.bind(mList.get(position));
    }

    @Override
    public final int getItemViewType(int position) {
        return mList.getType(position);
    }

    @Override
    public final int getItemCount() {
        return mList.size();
    }
}
