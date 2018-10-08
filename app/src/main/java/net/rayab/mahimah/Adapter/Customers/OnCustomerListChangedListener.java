package net.rayab.mahimah.Adapter.Customers;

import net.rayab.mahimah.Data.Datas;

import java.util.List;

//public interface OnCustomerListChangedListener<T>{
//    void onNoteListChanged(List<T> customers);
//}
public interface OnCustomerListChangedListener{
    void onNoteListChanged(List<Datas.MainItems> customers, int fromPosition, int toPosition);
}