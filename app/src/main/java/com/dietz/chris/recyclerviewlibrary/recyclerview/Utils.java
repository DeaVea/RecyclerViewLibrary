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
        if (listToCheck.size() == 0) {
            return  0;
        }
        int positionToCheck = listToCheck.size() / 2;
        AdapterItem itemToCheck = listToCheck.get(positionToCheck);
        int compare = item.compareTo(itemToCheck);
        if (compare == 0) {
            // Turns out this is the correct spot.  We're done!
            return positionToCheck;
        }

        int start = 0;
        int end = listToCheck.size() - 1;
        do {
            if (compare > 0) {
                // Sublist to the right
                start = positionToCheck;
                positionToCheck += Math.round(((float) end - (float) positionToCheck) / 2f);
            } else if (compare < 0) {
                end = positionToCheck;
                positionToCheck -= Math.round(((float) positionToCheck - (float) start) / 2f);
            }
            itemToCheck = listToCheck.get(positionToCheck);
            compare = item.compareTo(itemToCheck);
        } while (compare != 0 && (end - start) > 1);

        int returnPos;
        if (compare == 0) {
            returnPos = positionToCheck;
        } else if (positionToCheck == start) {
            returnPos = (compare < 0) ? start : start + 1;
        } else { // positionToCheck == end always
            returnPos = (compare < 0) ? end : end + 1;
        }
        returnPos = Math.max(0, returnPos);
        returnPos = Math.min(listToCheck.size(), returnPos);

        return returnPos;
    }
}
