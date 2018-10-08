package net.rayab.mahimah.Adapter.Customers;

import net.rayab.mahimah.Data.Datas;

import java.util.List;

public interface OnCustomerListChangedListenerOffer {
    void onNoteListChanged(List<Datas.SubServicesDiscount> customers);
}