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

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

/**
 * AdapterListener that sends everything to the provided {@link AdapterListener} on the Handler provided.
 */
public class HandlerListListener implements ListListener {

    private final Handler mHandler;

    private final ListListener mListListener;

    /**
     * Send everything to the main handler thread.
     */
    public HandlerListListener(@NonNull ListListener listener) {
        mHandler = new Handler(Looper.getMainLooper());
        mListListener = listener;
    }

    @Override
    public void onDatasetChanged() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListListener.onDatasetChanged();
            }
        });
    }

    @Override
    public void onItemChanged(final int atPosition, final AdapterItem payload) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListListener.onItemChanged(atPosition, payload);
            }
        });
    }

    @Override
    public void onItemInserted(final int atPosition, final AdapterItem payload) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListListener.onItemInserted(atPosition, payload);
            }
        });
    }

    @Override
    public void onItemMoved(final int fromPosition, final int toPosition, final AdapterItem payload) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListListener.onItemMoved(fromPosition, toPosition, payload);
            }
        });
    }

    @Override
    public void onItemRangedChanged(final int positionStart, final int itemCount) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListListener.onItemRangedChanged(positionStart, itemCount);
            }
        });
    }

    @Override
    public void onItemRangeInserted(final int positionStart, final int itemCount) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListListener.onItemRangeInserted(positionStart, itemCount);
            }
        });
    }

    @Override
    public void onItemRangeRemoved(final int positionStart, final int itemCount) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListListener.onItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    @Override
    public void onItemRemoved(final int position, final AdapterItem payload) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mListListener.onItemRemoved(position, payload);
            }
        });
    }
}
