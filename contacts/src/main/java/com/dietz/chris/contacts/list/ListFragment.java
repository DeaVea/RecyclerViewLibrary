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

package com.dietz.chris.contacts.list;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dietz.chris.contacts.R;
import com.dietz.chris.contacts.models.Contact;
import com.dietz.chris.recyclerviewlibrary.RecyclerAdapter;
import com.dietz.chris.recyclerviewlibrary.RecyclerItem;
import com.dietz.chris.recyclerviewlibrary.ViewHolder;

/**
 * Fragment that shows a list of contacts.
 */
public class ListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new RecyclerAdapter();
        mAdapter.setViewHolderFactory(new ViewHolderFactory());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.contact_list, group, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(group.getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(group.getContext(), DividerItemDecoration.VERTICAL));

        return root;
    }

    private static class ViewHolderFactory implements com.dietz.chris.recyclerviewlibrary.ViewHolderFactory {

        @Override
        public ViewHolder<? extends RecyclerItem> createViewHolder(final ViewGroup parent, int type) {
            return new ContactViewHolder(parent.getContext())
                    .addMainAction((actionId, boundItem) ->
                        Snackbar.make(parent, boundItem.name() + " main action clicked.", Snackbar.LENGTH_SHORT).show())
                    .addAction(R.id.imgProfile, (actionId, boundItem) ->
                            Snackbar.make(parent, boundItem.name() + " profile picture clicked.", Snackbar.LENGTH_SHORT).show())
                    .addAction(R.id.txtName, (actionId, boundItem) ->
                            Snackbar.make(parent, boundItem.name() + " name clicked.", Snackbar.LENGTH_SHORT).show())
                    .addLongClickMainAction((actionId, boundItem) ->
                            Snackbar.make(parent, boundItem.name() + " long clicked.", Snackbar.LENGTH_SHORT).show());
        }
    }

    private static class ContactViewHolder extends ViewHolder<Contact> {
        private ImageView mImg;
        private TextView mName;

        public ContactViewHolder(Context ctx) {
            super(ctx, R.layout.contact_item);
            mName = findView(R.id.txtName);
            mImg = findView(R.id.imgProfile);
        }

        @Override
        public void onBind(Contact item) {
            mName.setText(item.name());
        }
    }
}
