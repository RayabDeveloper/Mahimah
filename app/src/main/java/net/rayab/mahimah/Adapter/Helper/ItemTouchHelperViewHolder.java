package net.rayab.mahimah.Adapter.Helper;

/**
 * Created by safavie on 22/02/2018.
 */

public interface ItemTouchHelperViewHolder {
    /**
     * Implementations should update the item view to indicate it's active state.
     */
    void onItemSelected();


    /**
     * state should be cleared.
     */
    void onItemClear();
}