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

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *  @see android.support.v7.widget.RecyclerView.ViewHolder
 */
public abstract class ViewHolder<K extends RecyclerItem> extends RecyclerView.ViewHolder {

    private final InternalClickListener<K> mClickListener;
    private final MainClickListener<K> mMainClickListener;
    private K mBoundItem;

    /**
     * @param itemView
     *      View that backs this viewholder.
     */
    ViewHolder(View itemView) {
        super(itemView);
        mClickListener = new InternalClickListener<>();
        mMainClickListener = new MainClickListener<>();
        mBoundItem = null;
    }

    /**
     *
     * @param ctx
     *      Application context
     * @param id
     *      Layout ID of the layout to inflate
     */
    public ViewHolder(Context ctx, @LayoutRes int id) {
        this(LayoutInflater.from(ctx), id);
    }

    /**
     *
     * @param ctx
     *      Application context
     * @param parent
     *      Parent that the adapter is attached to (generally the RecyclerView)
     * @param id
     *      Layout ID of the layout to inflate
     */
    public ViewHolder(Context ctx, ViewGroup parent, @LayoutRes int id) {
        this(LayoutInflater.from(ctx), parent, id);
    }

    /**
     *
     * @param inflater
     *      LayoutInflater to inflate views
     * @param id
     *      Layout ID of the layout to inflate.
     */
    public ViewHolder(LayoutInflater inflater, @LayoutRes int id) {
        this(inflater, null, id);
    }

    /**
     *
     * @param inflater
     *      LayoutInflater to inflate views
     * @param parent
     *      Parent of the view holder (generally the RecyclerView itself)
     * @param id
     *      Layout ID of the layout to inflate.
     */
    public ViewHolder(LayoutInflater inflater, ViewGroup parent, @LayoutRes int id) {
        this(inflater.inflate(id, ((parent == null) ? new VoidViewGroup(inflater.getContext()) : parent), false));
    }

    /**
     * The bound item that is backing this ViewHolder.  Returns null if there is currently no bound
     * item.
     */
    public final K getBoundItem() {
        return mBoundItem;
    }

    /**
     * Used by the RecyclerAdapter to bind the item to this viewholder.
     * @param item
     *      Item to bind.  Can be null which will unbind it.
     */
    public final void bind(K item) {
        mBoundItem = item;
        mClickListener.bound(item);
        mMainClickListener.bind(mBoundItem);
        onBind(item);
    }

    /**
     * Overload this method to make the necessary changes to the backed view.
     * @param item
     *      The item that is currently bound to this viewholder.
     */
    public abstract void onBind(K item);

    /**
     * Convenience method to find a view in this viewholder with the given id.
     * @param id
     *      View ID of the view to find.
     * @param <T>
     *      Viewtype expected
     * @return
     *      View that was found in the viewholder.
     */
    public <T extends View> T findView(@IdRes int id) {
        //noinspection unchecked  It'll throw a ClassCastException anyway so the w
        return (T) itemView.findViewById(id);
    }

    /**
     * Adds an action to the main itemview backing this viewholder on a view click.
     * @param action
     *      The action to set.  If null, then the action will be removed.
     * @return
     *      This object for chaining.
     */
    public ViewHolder<K> addMainAction(Action<K> action) {
        mMainClickListener.setClickAction(action);
        if (action != null) {
            itemView.setOnClickListener(mMainClickListener);
        } else {
            itemView.setOnClickListener(null);
        }
        return this;
    }

    /**
     * Adds an action to the main itemview backing this viewholder on a view long click.
     * @param action
     *      The action to set.  If null, then the action will be removed.
     * @return
     *      This object for chaining.
     */
    public ViewHolder<K> addLongClickMainAction(Action<K> action) {
        mMainClickListener.setLongClickAction(action);
        if (action != null) {
            itemView.setOnLongClickListener(mMainClickListener);
        } else {
            itemView.setOnLongClickListener(null);
        }
        return this;
    }

    /**
     * Adds an action to the view with the given viewId on a view click.
     * @param viewId
     *      View Id of the view to set the action to.
     * @param action
     *      Action to add.  If null, then the action will be removed.
     * @return
     *      This object for chaining.
     */
    public ViewHolder<K> addAction(@IdRes int viewId, Action<K> action) {
        final View view = findView(viewId);
        if (view != null) {
            mClickListener.addAction(viewId, action);
            view.setOnClickListener(mClickListener);
        }
        return this;
    }

    /**
     * Click listener for the main itemView that this view holder backs.
     */
    private static class MainClickListener<K extends RecyclerItem> implements View.OnClickListener, View.OnLongClickListener {
        private Action<K> mClickAction;
        private Action<K> mLongClickAction;
        private K mBoundItem;

        public MainClickListener() {
            mClickAction = null;
            mLongClickAction = null;
        }

        public void setClickAction(Action<K> action) {
            mClickAction = action;
        }

        public void setLongClickAction(Action<K> action) {
            mLongClickAction = action;
        }

        public void bind(K item) {
            mBoundItem = item;
        }

        @Override
        public void onClick(View view) {
            if (mClickAction != null) {
                mClickAction.action(view.getId(), mBoundItem);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mLongClickAction != null) {
                mLongClickAction.action(v.getId(), mBoundItem);
                return true;
            }
            return false;
        }
    }

    /**
     * Sub click listener for various buttons that exist on the view.
     */
    private static class InternalClickListener<K extends RecyclerItem> implements View.OnClickListener {

        private final SparseArray<Action<K>> mClickActions;
        private K mBoundItem;

        public InternalClickListener() {
            mClickActions = new SparseArray<>(3);
        }

        public void bound(K item) {
            mBoundItem = item;
        }

        public void addAction(int viewId, Action<K> action) {
            if (action != null) {
                mClickActions.put(viewId, action);
            } else {
                mClickActions.remove(viewId);
            }
        }

        @Override
        public void onClick(View v) {
            final Action<K> action = mClickActions.get(v.getId());
            if (action != null) {
                action.action(v.getId(), mBoundItem);
            }
        }
    }

    /**
     * This is a ViewGroup used exclusively for inflating layouts with the LayoutInflater.
     * If the user doesn't pass one in and opts for a null, then the inflated layout loses
     * some attributes, so this is a default until something better comes along.
     */
    private static class VoidViewGroup extends ViewGroup {

        public VoidViewGroup(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {

        }
    }
}
