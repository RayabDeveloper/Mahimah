package net.rayab.mahimah.Activities.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.rayab.mahimah.R;
import net.rayab.mahimah.SQL.MySQLi;

public class fragOffer extends Fragment {
    @SuppressLint("StaticFieldLeak")
    static Context context;
    MySQLi SQL;
    static String OfferText = "";

    public static fragOffer newInstance(Context mContext, String OfferTexter) {
        fragOffer fragment = new fragOffer();
        context = mContext;
        OfferText = OfferTexter;
        return fragment;
    }

    TextView lblOffer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_offer, container, false);

        lblOffer = view.findViewById(R.id.lblOffer);

        lblOffer.setText(Html.fromHtml(OfferText.toString()));
//        lblOffer.setText("QWEQWE");

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
