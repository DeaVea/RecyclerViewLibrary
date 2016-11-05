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

package com.dietz.chris.contacts.models;

import android.support.annotation.NonNull;

import com.dietz.chris.recyclerviewlibrary.RecyclerItem;

/**
 * Contact model which contains all information related to the Contact.
 */
public class Contact implements RecyclerItem {
    private final String name;

    public Contact(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public String getIdentityKey() {
        return name;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int compareTo(@NonNull RecyclerItem recyclerItem) {
        return 0;
    }
}
