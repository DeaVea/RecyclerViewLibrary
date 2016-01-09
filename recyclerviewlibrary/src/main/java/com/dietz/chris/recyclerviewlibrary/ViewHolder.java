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
    private MainClickListener<K> mMainClickListener;
    private K mBoundItem;

    /**
     * @param itemView
     *      View that backs this viewholder.
     */
    ViewHolder(View itemView) {
        super(itemView);
        mClickListener = new InternalClickListener<>();
        mMainClickListener = null;
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
        this(inflater.inflate(id, null));
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
        this(inflater.inflate(id, parent, false));
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
        if (mMainClickListener != null) {
            mMainClickListener.bind(mBoundItem);
        }
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
        if (action != null) {
            itemView.setOnClickListener(mMainClickListener = new MainClickListener<K>(action));
            mMainClickListener.bind(mBoundItem);
        } else {
            itemView.setOnClickListener(mMainClickListener);
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
    private static class MainClickListener<K extends RecyclerItem> implements View.OnClickListener {
        private final Action<K> mAction;
        private K mBoundItem;

        public MainClickListener(Action<K> action) {
            mAction = action;
        }

        public void bind(K item) {
            mBoundItem = item;
        }

        @Override
        public void onClick(View view) {
            if (mAction != null) {
                mAction.action(view.getId(), mBoundItem);
            }
        }
    }

    /**
     * Sub click listener for various buttons that exist on the view.
     */
    private static class InternalClickListener<K extends RecyclerItem> implements View.OnClickListener {

        private final SparseArray<Action<K>> actions;
        private K mBoundItem;

        public InternalClickListener() {
            actions = new SparseArray<>(3);
        }

        public void bound(K item) {
            mBoundItem = item;
        }

        public void addAction(int viewId, Action<K> action) {
            if (action != null) {
                actions.put(viewId, action);
            } else {
                actions.remove(viewId);
            }
        }

        @Override
        public void onClick(View v) {
            final Action<K> action = actions.get(v.getId());
            if (action != null) {
                action.action(v.getId(), mBoundItem);
            }
        }
    }
}
