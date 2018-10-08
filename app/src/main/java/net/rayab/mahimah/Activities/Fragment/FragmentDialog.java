package net.rayab.mahimah.Activities.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import java.util.MissingFormatArgumentException;
import java.util.Objects;

/**
 * Fragment dialog displaying tab host...
 */
public class FragmentDialog extends DialogFragment
{
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    static Context context;
    static MySQLi SQL;

    TextView lblOffers;
    TextView lblSkills;
    TextView lblServices;
    TextView lblAllPrice;
    TextView lblAllPrice1;
    TextView lblCurrentPrice;
    TextView lblCurrentPrice1;

    static List<Datas.Counter> lCounter = new ArrayList<>();

    public static void FragmentDialoge(Context mContext, List<Datas.Counter> Cont){
        context = mContext;
        SQL = new MySQLi(context);
        lCounter = Cont;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog().getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_dialog_report, container);

        viewPager = view.findViewById(R.id.viewPagerr);
        lblOffers = view.findViewById(R.id.lblOffers);
        lblSkills = view.findViewById(R.id.lblSkills);
        lblServices = view.findViewById(R.id.lblServices);
        lblAllPrice = view.findViewById(R.id.lblAllPrice);
        lblAllPrice1 = view.findViewById(R.id.lblAllPrice1);
        lblCurrentPrice = view.findViewById(R.id.lblCurrentPrice);
        lblCurrentPrice1 = view.findViewById(R.id.lblCurrentPrice1);


        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblOffers.setTypeface(tFace);
        lblSkills.setTypeface(tFace);
        lblServices.setTypeface(tFace);
        lblAllPrice.setTypeface(tFace);
        lblAllPrice1.setTypeface(tFace);
        lblCurrentPrice.setTypeface(tFace);
        lblCurrentPrice1.setTypeface(tFace);

        SQL = new MySQLi(context);

        lblServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
        lblOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        lblSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        lblServices.setBackgroundColor(getResources().getColor(R.color.UpTitleUnSelecte));
                        lblServices.setTextColor(getResources().getColor(R.color.Gray));

                        lblOffers.setBackgroundColor(getResources().getColor(R.color.UpTitleUnSelecte));
                        lblOffers.setTextColor(getResources().getColor(R.color.Gray));

                        lblSkills.setBackgroundColor(getResources().getColor(R.color.UpTitleSelecte));
                        lblSkills.setTextColor(getResources().getColor(R.color.White));
                        break;
                    case 1:
                        lblServices.setBackgroundColor(getResources().getColor(R.color.UpTitleUnSelecte));
                        lblServices.setTextColor(getResources().getColor(R.color.Gray));

                        lblOffers.setBackgroundColor(getResources().getColor(R.color.UpTitleSelecte));
                        lblOffers.setTextColor(getResources().getColor(R.color.White));

                        lblSkills.setBackgroundColor(getResources().getColor(R.color.UpTitleUnSelecte));
                        lblSkills.setTextColor(getResources().getColor(R.color.Gray));
                        break;
                    case 2:
                        lblServices.setBackgroundColor(getResources().getColor(R.color.UpTitleSelecte));
                        lblServices.setTextColor(getResources().getColor(R.color.White));

                        lblOffers.setBackgroundColor(getResources().getColor(R.color.UpTitleUnSelecte));
                        lblOffers.setTextColor(getResources().getColor(R.color.Gray));

                        lblSkills.setBackgroundColor(getResources().getColor(R.color.UpTitleUnSelecte));
                        lblSkills.setTextColor(getResources().getColor(R.color.Gray));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        int mPrice = 0;
        int mPrice2 = 0;
        int cPrice = 0;
        String Mainer = "", Offerer = "", Skiller = "";
        int mI = 1;
        for (Datas.Counter data : lCounter) {
            Mainer += "روز " + mI + " : <br>";
            List<Datas.Skills> lSkill = new ArrayList<>();
            List<Datas.PriceNew> lPriceNew = new ArrayList<>();
            for (Datas.MainItems mData : data.lBaseItems()) {
                if(mData.isCheck()){
                    Mainer += mData.title() + "<br>" + mData.description() + "<br><br>";
                    if(mData.pricenew().length() > 1)
                        mPrice += Integer.parseInt(mData.pricenew().replace(",", ""));
                    else
                        mPrice += Integer.parseInt(mData.price().replace(",", ""));
                    mPrice2 += Integer.parseInt(mData.price().replace(",", ""));

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
            Mainer += "<br><br>";
            Offerer += "روز " + mI + " : <br>";
            for (Datas.PriceNew dataNew : lPriceNew) {
                Offerer += dataNew.title() + " - <font color='#DA5445'><strike>" + getPriceCamma(dataNew.price()) + "</strike></font> - " + getPriceCamma(dataNew.pricenew()) + "<br>";
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

            Skiller += "روز " + mI + " : <br>";
            for (Datas.SkillOffers dData : lDistinct) {
                Skiller += dData.name() + " : ";
                int skilPrice = 0;
                for (Datas.SkillOffers oData : lsOffer) {
                    if(dData.id() == oData.id()){
                        skilPrice += oData.price();
                    }
                }
                Skiller += getPriceCamma(skilPrice - dData.price()) + "<br>";
                cPrice += skilPrice - dData.price();
            }
            Skiller += "<br>";


            mI++;
        }


        lblAllPrice1.setText(getPriceCamma(mPrice2 - cPrice));
        lblCurrentPrice1.setText(getPriceCamma(mPrice - cPrice));

        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        sectionsPagerAdapter.init(context, Mainer, Offerer, Skiller);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(2);

        return view;
    }

    private String getPriceCamma(int Price){
        String Pricer = "";
        try {
            String value = Integer.toString(Price);
            String reverseValue = new StringBuilder(value).reverse().toString();
            StringBuilder finalValue = new StringBuilder();
            for (int i = 1; i <= reverseValue.length(); i++) {
                char val = reverseValue.charAt(i - 1);
                finalValue.append(val);
                if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                    finalValue.append(",");
                }
            }
            Pricer = finalValue.reverse().toString();
        } catch (Exception e) {
            // Do nothing since not a number
        }
        return Pricer;
    }
    private List<Datas.SkillOffers> removeDuplicateYears(final Collection<Datas.SkillOffers> awards) {
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
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        String Main = "", Offer = "", Skill = "";
        Context mContext;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        public void init(Context context, String man, String off, String sk){
            Main = man;
            Offer = off;
            Skill = sk;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
            {
                return fragSkill.newInstance(mContext, Skill);
            }
            if (position == 1)
            {
                return fragOffer.newInstance(mContext, Offer);
            }
            else if (position == 2)
            {
                return fragMainService.newInstance(mContext, Main);
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "First Tab";
                case 1:
                    return "Second Tab";
                case 2:
                    return "Third Tab";
            }
            return null;
        }
    }
}