package com.dietz.chris.recyclerviewlibrary.recyclerview;

import java.util.List;

/**
 *
 */
class Utils {

    /**
     * Retrieve the position of a new given item assuming that the list is currently in order.
     *
     * A value of 0 means the item should be placed at the beginning of a list while the value
     * listToCheck.size() means it should be placed at the end of the list.
     *
     * In other words, if list size is 8 and this returns a value of "8", then
     * {@link List#add(Object)} must be used to add it instead of {@link List#add(int, Object)}
     *
     * @param item
     *      Item to check.
     * @param listToCheck
     *      Ordered list.
     * @return
     *      The position that the item should be in.
     */
    public static int getPosition(AdapterItem item, List<? extends AdapterItem> listToCheck) {
        int start = 0;
        int end = listToCheck.size() - 1;
        int positionToCheck;
        AdapterItem itemToCheck;
        int compare;

        while (start <= end) {
            positionToCheck = (start + end) >>> 1;
            itemToCheck = listToCheck.get(positionToCheck);
            compare = item.compareTo(itemToCheck);
            if (compare < 0) {
                end = positionToCheck - 1;
            } else if (compare > 0){
                start = positionToCheck + 1;
            } else {
                return positionToCheck;
            }
        }
        return start;
    }
}
