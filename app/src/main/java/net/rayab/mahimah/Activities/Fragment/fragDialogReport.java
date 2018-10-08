package net.rayab.mahimah.Activities.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.rayab.mahimah.Adapter.FragmentAdapter;
import net.rayab.mahimah.R;
import net.rayab.mahimah.SQL.MySQLi;

import java.util.ArrayList;
import java.util.List;

public class fragDialogReport extends Fragment {
    @SuppressLint("StaticFieldLeak")
    static Context context;
    MySQLi SQL;

    FragmentAdapter fAdapter;

    ViewPager pager;

    List<Fragment> lFrag = new ArrayList<>();

    public static fragDialogReport newInstance(Context mContext) {
        fragDialogReport fragment = new fragDialogReport();
        context = mContext;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_dialog_report, container, false);


        try {
            lFrag = getFragments();
            fAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), lFrag);
            pager.setAdapter(fAdapter);
            pager.setCurrentItem(2);
//            imgHome.setImageDrawable(getResources().getDrawable(R.drawable.home_blue));
//            imgAccount.setImageDrawable(getResources().getDrawable(R.drawable.account));
//            imgReport.setImageDrawable(getResources().getDrawable(R.drawable.finance));
//            imgStore.setImageDrawable(getResources().getDrawable(R.drawable.store));
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<>();
//        fList.add(fragSkill.newInstance(getActivity()));
//        fList.add(fragOffer.newInstance(getActivity()));
//        fList.add(fragMainService.newInstance(getActivity()));
        return fList;

    }

}
