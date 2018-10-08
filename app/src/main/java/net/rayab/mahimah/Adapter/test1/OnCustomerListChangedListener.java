package net.rayab.mahimah.Adapter.test1;

import net.rayab.mahimah.Data.Datas;

import java.util.List;

public interface OnCustomerListChangedListener {
    void onNoteListChanged(List<Datas.MainItems> customers, List<Datas.MainItems> customers2);
}