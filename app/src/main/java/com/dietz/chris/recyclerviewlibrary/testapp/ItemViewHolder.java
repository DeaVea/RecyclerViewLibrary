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

package com.dietz.chris.recyclerviewlibrary.testapp;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dietz.chris.recyclerviewlibrary.ViewHolder;

/**
 *
 */
public class ItemViewHolder extends ViewHolder<LabelItem> {
    TextView mTv;

    /**
     * @param inflater
     *         LayoutInflater to inflate views
     * @param parent
     *         Parent of the view holder (generally the RecyclerView itself)
     * @param id
     *      Layout ID to inflate
     */
    public ItemViewHolder(LayoutInflater inflater, ViewGroup parent, @LayoutRes int id) {
        super(inflater, parent, id);
        mTv = findView(R.id.txtLabelName);
    }

    @Override
    public void onBind(LabelItem item) {
        mTv.setText(item.getIdentityKey());
    }
}
