package net.rayab.mahimah.Activities.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import net.rayab.mahimah.Activities.actMain;
import net.rayab.mahimah.Adapter.FragmentAdapter;
import net.rayab.mahimah.Data.Datas;
import net.rayab.mahimah.R;
import net.rayab.mahimah.SQL.MySQLi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class actTest extends AppCompatActivity {

    Context context = this;
    MySQLi SQL;

    TextView lblOffers;
    TextView lblSkills;
    TextView lblServices;
    TextView lblAllPrice;
    TextView lblAllPrice1;
    TextView lblCurrentPrice;
    TextView lblCurrentPrice1;
    ViewPager viewPagerr;

    public static List<Datas.Counter> lCounter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_dialog_report);
        this.setFinishOnTouchOutside(true);

        lblOffers = findViewById(R.id.lblOffers);
        lblSkills = findViewById(R.id.lblSkills);
        lblServices = findViewById(R.id.lblServices);
        lblAllPrice = findViewById(R.id.lblAllPrice);
        lblAllPrice1 = findViewById(R.id.lblAllPrice1);
        lblCurrentPrice = findViewById(R.id.lblCurrentPrice);
        lblCurrentPrice1 = findViewById(R.id.lblCurrentPrice1);
        viewPagerr = findViewById(R.id.viewPagerr);


        Typeface tFace = Typeface.createFromAsset(context.getAssets(), "fonts/ir_sans.ttf");
        lblOffers.setTypeface(tFace);
        lblSkills.setTypeface(tFace);
        lblServices.setTypeface(tFace);
        lblAllPrice.setTypeface(tFace);
        lblAllPrice1.setTypeface(tFace);
        lblCurrentPrice.setTypeface(tFace);
        lblCurrentPrice1.setTypeface(tFace);

        SQL = new MySQLi(context);

        FragmentAdapter fAdapter;
        List<Fragment> lFrag = new ArrayList<>();

        lblServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerr.setCurrentItem(2);
                lblServices.setTextColor(getResources().getColor(R.color.Blue));
                lblOffers.setTextColor(getResources().getColor(R.color.Black));
                lblSkills.setTextColor(getResources().getColor(R.color.Black));
            }
        });
        lblOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerr.setCurrentItem(1);
                lblServices.setTextColor(getResources().getColor(R.color.Black));
                lblOffers.setTextColor(getResources().getColor(R.color.Blue));
                lblSkills.setTextColor(getResources().getColor(R.color.Black));
            }
        });
        lblSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerr.setCurrentItem(0);
                lblServices.setTextColor(getResources().getColor(R.color.Black));
                lblOffers.setTextColor(getResources().getColor(R.color.Black));
                lblSkills.setTextColor(getResources().getColor(R.color.Blue));
            }
        });

        viewPagerr.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position){
                    case 0:
                        lblServices.setTextColor(getResources().getColor(R.color.Black));
                        lblOffers.setTextColor(getResources().getColor(R.color.Black));
                        lblSkills.setTextColor(getResources().getColor(R.color.Blue));
                        break;
                    case 1:
                        lblServices.setTextColor(getResources().getColor(R.color.Black));
                        lblOffers.setTextColor(getResources().getColor(R.color.Blue));
                        lblSkills.setTextColor(getResources().getColor(R.color.Black));
                        break;
                    case 2:
                        lblServices.setTextColor(getResources().getColor(R.color.Blue));
                        lblOffers.setTextColor(getResources().getColor(R.color.Black));
                        lblSkills.setTextColor(getResources().getColor(R.color.Black));
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //injame
        //Counter, MainItem
        int mPrice = 0;
        int mPrice2 = 0;
        int cPrice = 0;
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
            Offerer += "روز " + mI + " : <br>";
            for (Datas.PriceNew dataNew : lPriceNew) {
                Offerer += dataNew.title() + " - <font color='#DA5445'><strike>" + dataNew.price() + "</strike></font> - " + dataNew.pricenew() + "<br>";
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
                Skiller += Integer.toString(skilPrice - dData.price()) + "<br>";
                cPrice += skilPrice - dData.price();
            }
            Skiller += "<br>";


            mI++;
        }
        lblAllPrice1.setText(Integer.toString(mPrice2));
        lblCurrentPrice1.setText(Integer.toString(mPrice - cPrice));

        try {
            lFrag = getFragments(Mainer, Offerer, Skiller);
            fAdapter = new FragmentAdapter(getSupportFragmentManager(), lFrag);
            viewPagerr.setAdapter(fAdapter);
            viewPagerr.setCurrentItem(2);
            lblServices.setTextColor(getResources().getColor(R.color.Blue));
            lblOffers.setTextColor(getResources().getColor(R.color.Black));
            lblSkills.setTextColor(getResources().getColor(R.color.Black));
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }

    }

    @Override
    public void onBackPressed() {
        actMain.lCounter = lCounter;
        super.onBackPressed();
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
