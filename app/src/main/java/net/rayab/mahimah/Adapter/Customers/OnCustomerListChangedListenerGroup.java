package net.rayab.mahimah.Adapter.Customers;

import net.rayab.mahimah.Data.Datas;

import java.util.List;

public interface OnCustomerListChangedListenerGroup {
    void onNoteListChanged(List<Datas.Services> customers, int fromPosition, int toPosition);
}