package net.rayab.mahimah.Activities.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.rayab.mahimah.Adapter.FragmentAdapter;
import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;
import net.rayab.mahimah.SQL.MySQLi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class dFragment extends DialogFragment {

    static Context context;
    static List<Datas.Counter> lCounter = new ArrayList<>();
    static MySQLi SQL;
    static FragmentManager fm;

    public static void dFragmente(Context mContext, List<Datas.Counter> lCountere, FragmentManager kk){
        context = mContext;
        SQL = new MySQLi(mContext);
        lCounter = lCountere;
        fm = kk;
    }

    TextView lblServices;
    TextView lblOffers;
    TextView lblSkills;
    TextView lblAllPrice;
    TextView lblAllPrice1;
    TextView lblCurrentPrice;
    TextView lblCurrentPrice1;
    ViewPager pager;

    FragmentAdapter fAdapter;
    List<Fragment> lFrag = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_dialog_report, container, false);

        lblServices = rootView.findViewById(R.id.lblServices);
        lblOffers = rootView.findViewById(R.id.lblOffers);
        lblSkills = rootView.findViewById(R.id.lblSkills);
        lblAllPrice = rootView.findViewById(R.id.lblAllPrice);
        lblAllPrice1 = rootView.findViewById(R.id.lblAllPrice1);
        lblCurrentPrice = rootView.findViewById(R.id.lblCurrentPrice);
        lblCurrentPrice1 = rootView.findViewById(R.id.lblCurrentPrice1);
        pager = rootView.findViewById(R.id.viewPagerr);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ir_sans.ttf");
        lblServices.setTypeface(tf);lblOffers.setTypeface(tf);lblSkills.setTypeface(tf);
        lblAllPrice.setTypeface(tf);lblAllPrice1.setTypeface(tf);lblCurrentPrice.setTypeface(tf);
        lblCurrentPrice1.setTypeface(tf);

        getDialog().setTitle("AAA");


        lblServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
                lblServices.setTextColor(getResources().getColor(R.color.Blue));
                lblOffers.setTextColor(getResources().getColor(R.color.Black));
                lblSkills.setTextColor(getResources().getColor(R.color.Black));
            }
        });
        lblOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
                lblServices.setTextColor(getResources().getColor(R.color.Black));
                lblOffers.setTextColor(getResources().getColor(R.color.Blue));
                lblSkills.setTextColor(getResources().getColor(R.color.Black));
            }
        });
        lblSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
                lblServices.setTextColor(getResources().getColor(R.color.Black));
                lblOffers.setTextColor(getResources().getColor(R.color.Black));
                lblSkills.setTextColor(getResources().getColor(R.color.Blue));
            }
        });
        //injame
        //Counter, MainItem
        int mPrice = 0;
        String Mainer = "", Offerer = "", Skiller = "";
        int mI = 1;
        for (Datas.Counter data : lCounter) {
            List<Datas.Skills> lSkill = new ArrayList<>();
            List<Datas.PriceNew> lPriceNew = new ArrayList<>();
            for (Datas.MainItems mData : data.lBaseItems()) {
                if(mData.isCheck()){
                    Mainer += "روز " + mI + " : " + mData.title() + "<br>" + mData.description() + "<br><br>";
                    if(mData.pricenew().length() > 1)
                        mPrice += Integer.parseInt(mData.pricenew().replace(",", ""));
                    else
                        mPrice += Integer.parseInt(mData.price().replace(",", ""));

                    if(mData.pricenew().length() > 1){
                        Datas.PriceNew dPriceNew = new Datas.PriceNew();
                        dPriceNew.price(Integer.parseInt(mData.price().replace(",", "")));
                        dPriceNew.pricenew(Integer.parseInt(mData.pricenew().replace(",", "")));
                        dPriceNew.title(mData.title());

                        lPriceNew.add(dPriceNew);
                    }
                    SkillFinder:
                    {
                        List<Datas.SkillMain> lSkillMain = SQL.Select(Datas.getSkillMain, Datas.SkillMain.class, context);
                        for (Datas.SkillMain lData : lSkillMain) {
                            List<Datas.SkillSub> lSkillSub = SQL.Select(Datas.getSkillSub(lData.id()), Datas.SkillSub.class, context);
                            for (Datas.SkillSub nData : lSkillSub) {
                                if (nData.service_id() == mData.service_id()) {
                                    Datas.Skills sData = new Datas.Skills();
                                    sData.id(lData.id());
                                    sData.name(lData.name());
                                    sData.price(lData.price());
                                    sData.service_id(nData.service_id());

                                    lSkill.add(sData);
                                }
                            }
                        }
                    }
                }
            }
            Offerer += "روز " + mI + " : <br>";
            for (Datas.PriceNew dataNew : lPriceNew) {
                Offerer += dataNew.title() + " - " + dataNew.price() + " - " + dataNew.pricenew() + "<br>";
            }
            Offerer += "<br>";
            List<Datas.SkillOffers> lsOffer = new ArrayList<>();
            for (Datas.Skills skilData : lSkill) {
                Datas.SkillOffers datOffer = new Datas.SkillOffers();
                datOffer.id(skilData.id());
                datOffer.name(skilData.name());
                datOffer.price(skilData.price());

                lsOffer.add(datOffer);
            }
            List<Datas.SkillOffers> lDistinct = removeDuplicateYears(lsOffer);


            for (Datas.SkillOffers dData : lDistinct) {
                Skiller += dData.name() + " : ";
                int skilPrice = 0;
                for (Datas.SkillOffers oData : lsOffer) {
                    if(dData.id() == oData.id()){
                        skilPrice += oData.price();
                    }
                }
                Skiller += Integer.toString(skilPrice - dData.price()) + "<br>";
            }


            mI++;
        }

        try {
            lFrag = getFragments(Mainer, Offerer, Skiller);
            fAdapter = new FragmentAdapter(fm, lFrag);
            pager.setAdapter(fAdapter);
//            pager.setCurrentItem(2);
            lblServices.setTextColor(getResources().getColor(R.color.Blue));
            lblOffers.setTextColor(getResources().getColor(R.color.Black));
            lblSkills.setTextColor(getResources().getColor(R.color.Black));
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }

        return rootView;
    }

    private List<Fragment> getFragments(String Main, String Offer, String Skill){
        List<Fragment> fList = new ArrayList<>();
        fList.add(fragSkill.newInstance(context, Skill));
        fList.add(fragOffer.newInstance(context, Offer));
        fList.add(fragMainService.newInstance(context, Main));
        return fList;

    }
    public static List<Datas.SkillOffers> removeDuplicateYears(final Collection<Datas.SkillOffers> awards) {
        final ArrayList<Datas.SkillOffers> input = new ArrayList<>(awards);
        // If there's only one element (or none), guaranteed unique.
        if (input.size() <= 1) {
            return input;
        }
        final HashSet<Integer> years = new HashSet<Integer>(input.size(), 1);
        final Iterator<Datas.SkillOffers> iter = input.iterator();
        while(iter.hasNext()) {
            final Datas.SkillOffers award = iter.next();
            final Integer year = award.id();
            if (years.contains(year)) {
                iter.remove();
            } else {
                years.add(year);
            }
        }
        return input;

    }

}
