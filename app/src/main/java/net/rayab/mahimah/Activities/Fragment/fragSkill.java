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

public class fragSkill extends Fragment {
    @SuppressLint("StaticFieldLeak")
    static Context context;
    MySQLi SQL;
    static String SkillText = "";

    public static fragSkill newInstance(Context mContext, String SkillTexter) {
        fragSkill fragment = new fragSkill();
        context = mContext;
        SkillText = SkillTexter;
        return fragment;
    }

    TextView lblSkill;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_skill, container, false);

        lblSkill = view.findViewById(R.id.lblSkill);

        lblSkill.setText(Html.fromHtml(SkillText.toString()));

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
