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

import android.support.annotation.NonNull;

import com.dietz.chris.recyclerviewlibrary.RecyclerItem;

/**
 *
 */
public class GroupItem implements RecyclerItem {

    static int count = 0;

    private final int type;
    private final String LABEL;

    public GroupItem(int type) {
        LABEL = "Group Item " + ++count;
        this.type = type;
    }

    @NonNull
    @Override
    public String getIdentityKey() {
        return LABEL;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public int compareTo(@NonNull RecyclerItem another) {
        return getIdentityKey().compareTo(another.getIdentityKey());
    }
}
