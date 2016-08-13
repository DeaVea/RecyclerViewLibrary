package com.dietz.chris.recyclerviewlibrary.core;

/**
 *
 */
public interface Filter<T> {
    boolean accept(T value);
}
