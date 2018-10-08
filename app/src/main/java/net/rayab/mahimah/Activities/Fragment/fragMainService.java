package net.rayab.mahimah.Activities.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.rayab.mahimah.R;
import net.rayab.mahimah.SQL.MySQLi;

public class fragMainService extends Fragment {
    @SuppressLint("StaticFieldLeak")
    static Context context;
    MySQLi SQL;
    static String MainServiceText = "";

    public static fragMainService newInstance(Context mContext, String MainServiceTexter) {
        fragMainService fragment = new fragMainService();
        context = mContext;
        MainServiceText = MainServiceTexter;
        return fragment;
    }

    TextView lblMainServices;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_main_service, container, false);

        lblMainServices = view.findViewById(R.id.lblMainServices);

        lblMainServices.setText(Html.fromHtml(MainServiceText.toString()));
//        lblMainServices.setText("ASD");

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
